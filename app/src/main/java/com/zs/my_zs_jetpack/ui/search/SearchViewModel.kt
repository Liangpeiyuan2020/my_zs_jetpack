package com.zs.my_zs_jetpack.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.CollectionState
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import com.zs.my_zs_jetpack.utils.MyToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SearchViewModel : BaseModel() {
    val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val repo = ArticleRepository(retrofit)

    private var _pagerNum = 0
    private var _keyWords = ""
    private var _articles1 = MutableLiveData<MutableList<Article>>()
    val articles1: LiveData<MutableList<Article>> = _articles1

    fun search1(keyWords: String) {
        viewModelScope.launch {
            _pagerNum = 0
            _keyWords = keyWords
            _articles1.value = callApi { repo.search1(_pagerNum, _keyWords) } ?: mutableListOf()
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            _pagerNum += 1
            val list = repo.search1(_pagerNum, _keyWords)
            if (list.isNotEmpty()) {
                // 创建新列表并设置value
                val newList = (_articles1.value ?: mutableListOf()).apply {
                    addAll(list)
                }

                _articles1.value = newList
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _pagerNum = 0
            _articles1.value = callApi { repo.search1(_pagerNum, _keyWords) } ?: mutableListOf()
        }
    }

    // 记录所有点击过的文章的状态缓存
    private val stateCache = mutableMapOf<Int, CollectionState>()

    // 单事件通知流
    private val _collectionUpdates = MutableSharedFlow<CollectionState>(extraBufferCapacity = 10)
    val collectionUpdates: SharedFlow<CollectionState> = _collectionUpdates.asSharedFlow()


    fun clearStateCache() {
        stateCache.clear()
    }

    // 处理收藏/取消收藏操作
    fun handleCollection(articleId: Int, articleCollect: Boolean) {
        // 从缓存获取当前点击项状态（或创建默认状态）
        val nowCollectState = if (stateCache.containsKey(articleId))
            stateCache[articleId]!!.isCollected // 初始状态
        else
            articleCollect // 初始状态

        val shouldCollect = !nowCollectState
        var currentState = CollectionState(
            articleId,
            true,
            nowCollectState
        )
        viewModelScope.launch {
            // 先触发ui更新，变为正在收藏或取消收藏中
            _collectionUpdates.emit(currentState)
            try {
                // 2. 发起网络请求
                val response = if (shouldCollect) {
                    repo.collect(articleId)
                } else {
                    repo.unCollect(articleId)
                }

                // 3. 根据响应结果更新最终状态
                if (response.errorCode == 0) {
                    currentState = CollectionState(articleId, false, shouldCollect)
                } else {
                    MyToast.toast("请先登录")
                    // 失败时恢复原状态
                    currentState = CollectionState(articleId, false, nowCollectState)
                }
            } catch (e: Exception) {
                // 出错时恢复原状态
                currentState = CollectionState(articleId, false, nowCollectState)
            } finally {
                //触发ui更新，变为接口返回的结果
                _collectionUpdates.emit(currentState)
                //存储数据
                stateCache[articleId] = currentState
            }
        }
    }
}