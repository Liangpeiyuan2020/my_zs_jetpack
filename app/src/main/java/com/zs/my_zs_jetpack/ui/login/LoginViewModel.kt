package com.zs.my_zs_jetpack.ui.login

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiResponse
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.api.UserBean
import com.zs.my_zs_jetpack.common_base.BaseModel
import com.zs.my_zs_jetpack.constants.Constants
import kotlinx.coroutines.launch

class LoginViewModel : BaseModel() {
    val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val repo = ArticleRepository(retrofit)
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var passIsVisibility = MutableLiveData<Boolean>()
    var loginRes = MutableLiveData<ApiResponse<UserBean>?>()

    fun login() {
        if (TextUtils.isEmpty(username.value) || TextUtils.isEmpty(password.value)) return
        viewModelScope.launch {
            loginRes.value = callApi { repo.login(username.value!!, password.value!!) }
            Log.i("setIntegralBean20", loginRes.value.toString())
            if (loginRes.value?.errorCode == 0) {
                MyPreUtils.setObject(Constants.USER_INFO, loginRes.value!!.data)
                MyPreUtils.setBoolean(Constants.LOGIN, true)
            } else {
                MyPreUtils.clearKey(Constants.USER_INFO)
                MyPreUtils.setBoolean(Constants.LOGIN, false)
            }

        }
    }
}