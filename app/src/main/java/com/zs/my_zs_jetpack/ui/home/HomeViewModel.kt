package com.zs.my_zs_jetpack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiPage
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.RetrofitManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class HomeViewModel : ViewModel() {

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
}