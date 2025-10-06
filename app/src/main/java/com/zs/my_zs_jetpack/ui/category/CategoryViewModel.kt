package com.zs.my_zs_jetpack.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.CollectionState
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import com.zs.my_zs_jetpack.utils.MyToast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlin.collections.set

class CategoryViewModel : BaseModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)
    private val repo = ArticleRepository(retrofit)


    // 记录所有点击过的文章的状态缓存
    private val stateCache = mutableMapOf<Int, CollectionState>()
    // 单事件通知流
    private val _collectionUpdates = MutableSharedFlow<CollectionState>(extraBufferCapacity = 10)
    val collectionUpdates: SharedFlow<CollectionState> = _collectionUpdates.asSharedFlow()

    private var _articleTableId = MutableStateFlow<Int>(0)
    var _articles = _articleTableId
        .flatMapLatest { query ->
            repo.getSystemArticle(query).cachedIn(viewModelScope)
        }.shareIn(viewModelScope, SharingStarted.Lazily)

    val articles get() = _articles

    fun loadData(value: Int) {
        if (value !== _articleTableId.value)
            _articleTableId.value = value
    }


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