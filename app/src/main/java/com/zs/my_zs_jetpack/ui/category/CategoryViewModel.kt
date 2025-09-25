package com.zs.my_zs_jetpack.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn

class CategoryViewModel : BaseModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)
    private val repo = ArticleRepository(retrofit)

    private var _articleTableId = MutableStateFlow<Int>(0)
    private var _articles = _articleTableId.flatMapLatest { query ->
        repo.getSystemArticle(query).cachedIn(viewModelScope)
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    val articles get() = _articles

    fun loadData(value: Int) {
        if (value !== _articleTableId.value)
            _articleTableId.value = value
    }
}