package com.zs.my_zs_jetpack.ui.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiResponse
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import kotlinx.coroutines.launch


class RegisterViewModel : BaseModel() {
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var passIsVisibility = MutableLiveData<Boolean>(false)
    var rePassword = MutableLiveData<String>()
    var rePassIsVisibility = MutableLiveData<Boolean>(false)
    val registerLiveData = MutableLiveData<ApiResponse<Any>?>()

    private val retrofit = RetrofitManage.getService(ApiServices::class.java)
    private val repo = ArticleRepository(retrofit)

    fun register() {
        if (TextUtils.isEmpty(username.value) ||
            TextUtils.isEmpty(password.value) ||
            TextUtils.isEmpty(rePassword.value)
        ) return
        viewModelScope.launch {
            registerLiveData.value =
                callApi { repo.register(username.value!!, password.value!!, rePassword.value!!) }
        }
    }
}