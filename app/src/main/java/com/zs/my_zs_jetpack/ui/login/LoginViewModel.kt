package com.zs.my_zs_jetpack.ui.login

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
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
    var loginRes = MutableLiveData<Boolean>(false)
    var loginMes = MutableLiveData<String>()

    fun login() {
        if (TextUtils.isEmpty(username.value) || TextUtils.isEmpty(password.value)) return
        viewModelScope.launch {
            val data = callApi { repo.login(username.value!!, password.value!!) }
            Log.i("setIntegralBean20", data.toString())
            if (data?.errorCode == 0) {

                Log.i("setIntegralBean20", data.data.toString())
                loginRes.value = true
                MyPreUtils.setObject(Constants.USER_INFO, data.data)
                MyPreUtils.setBoolean(Constants.LOGIN, true)
            } else {
                MyPreUtils.clearKey(Constants.USER_INFO)
                MyPreUtils.setBoolean(Constants.LOGIN, false)
                loginMes.value = data?.errorMsg
                loginRes.value = false
            }

        }
    }
}