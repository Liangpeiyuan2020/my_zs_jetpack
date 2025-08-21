package com.zs.my_zs_jetpack.ui.tab


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.CollectionState
import com.zs.my_zs_jetpack.api.RetrofitManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)
    private val repository = ArticleRepository(retrofit)

    // 记录所有点击过的文章的状态缓存
    private val stateCache = mutableMapOf<Int, CollectionState>()

    // 单事件通知流
    private val _collectionUpdates = MutableSharedFlow<CollectionState>(extraBufferCapacity = 10)
    val collectionUpdates: SharedFlow<CollectionState> = _collectionUpdates.asSharedFlow()

    private val _accountTableId = MutableStateFlow<Int?>(null)
    val accountTabItemArticles: Flow<PagingData<Article>> = _accountTableId
        .flatMapLatest { query ->
            repository.getAccountArticleList(query!!).cachedIn(viewModelScope)
        }.shareIn(viewModelScope, SharingStarted.Lazily) // 状态流共享

    fun loadData(query: Int) {
        if (_accountTableId.value != query) {
            _accountTableId.value = query
        }
    }

    fun handleCollection(articleId: Int, shouldCollect: Boolean) {
        var currentState = if (stateCache.containsKey(articleId))
            CollectionState(
                articleId,
                true,
                !stateCache[articleId]!!.isCollected
            )
        else
            CollectionState(
                articleId,
                true,
                shouldCollect
            )
        val reallyShouldCollect = currentState.isCollected
        viewModelScope.launch {
            // 先触发ui更新，变为正在收藏或取消收藏中
            _collectionUpdates.emit(currentState)
            try {
                // 2. 发起网络请求
                val response = if (reallyShouldCollect) {
                    repository.collect(articleId)
                } else {
                    repository.unCollect(articleId)
                }

                // 3. 根据响应结果更新最终状态
                if (response.errorCode == 0) {
                    currentState = CollectionState(articleId, false, reallyShouldCollect)
                } else {
                    // 失败时恢复原状态
                    currentState = CollectionState(articleId, false, !reallyShouldCollect)
                }
            } catch (e: Exception) {
                // 出错时恢复原状态
                currentState = CollectionState(articleId, false, !reallyShouldCollect)
            } finally {
                //触发ui更新，变为接口返回的结果
                _collectionUpdates.emit(currentState)
                //存储数据
                stateCache[articleId] = currentState
            }
        }
    }
}
