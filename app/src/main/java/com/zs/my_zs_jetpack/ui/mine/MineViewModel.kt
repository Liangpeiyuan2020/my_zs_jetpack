package com.zs.my_zs_jetpack.ui.mine

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_zs_jetpack.Repository.ArticleRepository
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.IntegralBean
import com.zs.my_zs_jetpack.api.RetrofitManage
import com.zs.my_zs_jetpack.common_base.BaseModel
import com.zs.my_zs_jetpack.constants.Constants
import com.zs.my_zs_jetpack.utils.CacheUtil
import com.zs.my_zs_jetpack.utils.PrefUtils
import kotlinx.coroutines.launch

class MineViewModel : BaseModel() {
    val username = MutableLiveData<String>("请先登录")
    val id = MutableLiveData<String>("---")
    val rank = MutableLiveData<String>("0")
    var internal = MutableLiveData<String>("0")
    val internalLiveData = MutableLiveData<IntegralBean>()
    val retrofit = RetrofitManage.getService(ApiServices::class.java)
    val repo = ArticleRepository(retrofit)

    fun getInternalBean() {
        viewModelScope.launch {
            var integralBean: IntegralBean? = null
            MyPreUtils.getObject(Constants.INTEGRAL_INFO, IntegralBean::class.java)?.let {
                //先从本地获取积分，获取不到再通过网络获取
                integralBean = it
            }
            Log.i("setIntegralBean2", integralBean.toString())
            if (integralBean == null) {
                Log.i("setIntegralBean6", MyPreUtils.getBoolean(Constants.LOGIN, false).toString())
                if (MyPreUtils.getBoolean(Constants.LOGIN, false)) {
                    Log.i("setIntegralBean3", integralBean.toString())
                    val data = callApi { repo.getInternal() }
                    Log.i("setIntegralBean4", data.toString())
                    setIntegralBean(data)
                    MyPreUtils.setObject(Constants.INTEGRAL_INFO, data)
                }
            } else {
                setIntegralBean(integralBean)
            }

        }
    }

    private fun setIntegralBean(integralBean: IntegralBean?) {
        Log.i("setIntegralBean", integralBean.toString())
        integralBean?.let {
            username.value = it.username ?: "未知用户"
            id.value = it.userId.toString()
            rank.value = it.rank
            internal.value = it.coinCount.toString()
            internalLiveData.value = integralBean!!
        }


    }

}