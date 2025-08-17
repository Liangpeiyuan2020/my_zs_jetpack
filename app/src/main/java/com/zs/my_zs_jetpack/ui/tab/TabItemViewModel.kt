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
import kotlinx.coroutines.flow.flatMapLatest

class TabItemViewModel : ViewModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)

    private val repository = ArticleRepository(retrofit)

    private var tableId = 402
    private val _tableId = MutableStateFlow<Int>(tableId)

    val tabItemArticles: Flow<PagingData<AllDataBean>> = _tableId
        .flatMapLatest { query ->
            repository.getProjectList(query).cachedIn(viewModelScope)
        }


    fun loadData(query: Int) {
        tableId = query
        _tableId.value = tableId
    }

}