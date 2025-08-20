package com.zs.my_zs_jetpack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.RetrofitManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import com.zs.my_zs_jetpack.api.CollectionState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // 私有状态流，保存所有文章的状态
    private val _collectionStates = MutableStateFlow<Map<Int, CollectionState>>(emptyMap())

    // 公开只读状态流
    val collectionStates: StateFlow<Map<Int, CollectionState>> = _collectionStates.asStateFlow()

    private val retrofit = RetrofitManage.getService(ApiServices::class.java)

    private val repository = ArticleRepository(retrofit)

    // 刷新触发机制
    private val _refreshTrigger = MutableStateFlow<Boolean>(false)
    val articles: Flow<PagingData<Article>> = _refreshTrigger
        .flatMapLatest {
            repository.getArticleList().cachedIn(viewModelScope)
        }

    fun refresh() {
        _refreshTrigger.value = !_refreshTrigger.value
    }

    // 更新单条状态的方法
    private fun updateCollectionState(articleId: Int, isCollecting: Boolean, isCollected: Boolean) {
        _collectionStates.update { currentMap ->
            currentMap.toMutableMap().apply {
                this[articleId] = CollectionState(articleId, isCollecting, isCollected)
            }
        }
    }
    // 处理收藏/取消收藏操作
    fun handleCollection(articleId: Int, shouldCollect: Boolean) {
        // 1. 立即更新本地状态为"操作中"
        updateCollectionState(articleId, true, shouldCollect)

        viewModelScope.launch {
            try {
                // 2. 发起网络请求
                val response = if (shouldCollect) {
                    repository.collect(articleId)
                } else {
                    repository.unCollect(articleId)
                }

                // 3. 根据响应结果更新最终状态
                if (response.errorCode == 0) {
                    updateCollectionState(articleId, false, shouldCollect)
                } else {
                    // 失败时恢复原状态
                    updateCollectionState(articleId, false, !shouldCollect)
                }
            } catch (e: Exception) {
                // 出错时恢复原状态
                updateCollectionState(articleId, false, !shouldCollect)
            }
        }
    }
}