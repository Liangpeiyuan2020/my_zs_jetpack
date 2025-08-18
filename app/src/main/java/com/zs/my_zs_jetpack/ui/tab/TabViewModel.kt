package com.zs.my_zs_jetpack.ui.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.ArticleTab
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import kotlinx.coroutines.launch

class TabViewModel : BaseModel() {
    private var _articleTab = MutableLiveData<List<ArticleTab>?>()
    val articleTab: LiveData<List<ArticleTab>?> = _articleTab


    val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val rep = ArticleRepository(retrofit)

    fun getTab(type: Int) {
        viewModelScope.launch {
            when (type) {
                0 -> _articleTab.value = callApi { rep.getArticleTab() }
                1 -> _articleTab.value = callApi { rep.getAccountTab() }
            }
        }
    }
}