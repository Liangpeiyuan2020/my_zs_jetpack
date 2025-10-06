package com.zs.my_zs_jetpack.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import com.zs.my_zs_jetpack.constants.Constants
import com.zs.my_zs_jetpack.event.LogoutEvent
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class SettingsViewModel : BaseModel() {
    var logoutLiveData = MutableLiveData<Any?>()

    val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val repo = ArticleRepository(retrofit)

    fun logout() {
        viewModelScope.launch {
            logoutLiveData.value = repo.logout()
            resetUser()
        }
    }

    private fun resetUser() {
        MyPreUtils.clearKey(Constants.USER_INFO)
        MyPreUtils.clearKey(Constants.LOGIN)
        MyPreUtils.clearKey(Constants.INTEGRAL_INFO)
        EventBus.getDefault().post(LogoutEvent())
    }
}