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
import com.zs.my_zs_jetpack.api.RetrofitManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn

class TabItemViewModel : ViewModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)

    private val repository = ArticleRepository(retrofit)
    private var _type: Int = 0

    private val _tableId = MutableStateFlow<Int?>(null)

    val tabItemArticles: Flow<PagingData<AllDataBean>> = _tableId
        .flatMapLatest { query ->
            repository.getTabArticleList(query!!, _type).cachedIn(viewModelScope)
        }.shareIn(viewModelScope, SharingStarted.Lazily) // 状态流共享


    fun loadData(query: Int, type: Int) {
        if (_tableId.value != query) {
            _tableId.value = query
            _type = type
        }
    }

}