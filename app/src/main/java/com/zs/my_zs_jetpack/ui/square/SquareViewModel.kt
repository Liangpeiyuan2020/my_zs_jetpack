package com.zs.my_zs_jetpack.ui.square

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.NavigationBean
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.api.SquareBaseBean
import com.zs.my_zs_jetpack.api.SystemBean
import com.zs.my_zs_jetpack.common_base.BaseModel
import kotlinx.coroutines.launch

class SquareViewModel : BaseModel() {
    private val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val repo = ArticleRepository(retrofit)

//    private var _systemList = MutableLiveData<List<SystemBean>?>()
//    val systemList: LiveData<List<SystemBean>?> = _systemList
//
//    private var _navigationList = MutableLiveData<List<NavigationBean>?>()
//    val navigationList: LiveData<List<NavigationBean>?> = _navigationList

    private var _itemList = MutableLiveData<List<SquareBaseBean>?>()
    val itemList: LiveData<List<SquareBaseBean>?> = _itemList

    fun getSystemList() {
        viewModelScope.launch {
            val data = callApi { repo.getSystemList() }
//            _systemList.value = data
            Log.i("getSystemList", data?.size.toString())
            data?.let {

                _itemList.value = it.map { system ->
                    SquareBaseBean(
                        system.name,
                        system.children.map { child -> child.name }
                    )
                }
            }
        }
    }

    fun getNavigationList() {
        viewModelScope.launch {
            val data = callApi { repo.getNavigationList() }
//            _navigationList.value = data
            Log.i("getSystemList1", data?.size.toString())
            data?.let {

                _itemList.value = it.map { nav ->
                    SquareBaseBean(
                        nav.name,
                        nav.articles.map { article -> article.title }
                    )
                }
            }
        }
    }

}