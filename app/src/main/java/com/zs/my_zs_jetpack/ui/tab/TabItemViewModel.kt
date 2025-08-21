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

class TabItemViewModel : ViewModel() {

    // 记录所有点击过的文章的状态缓存
    private val stateCache = mutableMapOf<Int, CollectionState>()

    //    // 私有状态流，保存所有文章的状态
//    private val _collectionStates = MutableStateFlow<Map<Int, CollectionState>>(emptyMap())
//    // 公开只读状态流
//    val collectionStates: StateFlow<Map<Int, CollectionState>> = _collectionStates.asStateFlow()


    // 单事件通知流
    private val _collectionUpdates = MutableSharedFlow<CollectionState>(extraBufferCapacity = 10)
    val collectionUpdates: SharedFlow<CollectionState> = _collectionUpdates.asSharedFlow()

    private val retrofit = RetrofitManage.getService(ApiServices::class.java)

    private val repository = ArticleRepository(retrofit)

    private val _projectTableId = MutableStateFlow<Int?>(null)
    private val _accountTableId = MutableStateFlow<Int?>(null)


    val projectTabItemArticles: Flow<PagingData<AllDataBean>> = _projectTableId
        .flatMapLatest { query ->
            repository.getProjectArticleList(query!!).cachedIn(viewModelScope)
        }.shareIn(viewModelScope, SharingStarted.Lazily) // 状态流共享

    val accountTabItemArticles: Flow<PagingData<Article>> = _accountTableId
        .flatMapLatest { query ->
            repository.getAccountArticleList(query!!).cachedIn(viewModelScope)
        }.shareIn(viewModelScope, SharingStarted.Lazily) // 状态流共享

    fun loadData(query: Int, type: Int) {
        when (type) {
            0 -> if (_projectTableId.value != query) {
                _projectTableId.value = query
            }

            1 -> if (_accountTableId.value != query) {
                _accountTableId.value = query
            }
        }

    }

    fun handleCollection(articleId: Int, shouldCollect: Boolean) {
        val currentState = if (stateCache.containsKey(articleId)) CollectionState(
            articleId,
            true,
            !stateCache[articleId]!!.isCollected
        )
        else CollectionState(
            articleId,
            true,
            shouldCollect
        )
        val reallyShouldCollect = currentState.isCollected
        viewModelScope.launch {
            _collectionUpdates.emit(currentState)
        }
    }

}