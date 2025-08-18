package com.zs.my_zs_jetpack.ui.square

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.NavigationBean
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.api.SystemBean
import com.zs.my_zs_jetpack.common_base.BaseModel
import kotlinx.coroutines.launch

class SquareViewModel : BaseModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val repo = ArticleRepository(retrofit)

    private var _systemList = MutableLiveData<List<SystemBean>?>()
    val systemList: LiveData<List<SystemBean>?> = _systemList

    private var _navigationList = MutableLiveData<List<NavigationBean>?>()
    val navigationList: LiveData<List<NavigationBean>?> = _navigationList

    fun getSystemList() {
        viewModelScope.launch {
            _systemList.value = callApi {
                repo.getSystemList()
            }
        }
    }

    fun getNavigationList() {
        viewModelScope.launch {
            _navigationList.value = callApi {
                repo.getNavigationList()
            }
        }
    }

}