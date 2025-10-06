package com.zs.my_zs_jetpack.common_base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.zs.my_zs_jetpack.AppGlobals

abstract class BaseModel : ViewModel() {
    protected suspend fun <T> callApi(
        apiCall: suspend () -> T
    ): T? {
        AppGlobals.showGlobalLoading()
        try {
            val result = apiCall()
            AppGlobals.hideGlobalLoading()
            return result
        } catch (e: Exception) {
            AppGlobals.hideGlobalLoading()
            handleApiError(e)
            Log.e("apiRequestError", e.message.toString())
            return null
        }
    }

    // 子类可以覆盖此方法实现特定的错误处理
    protected open fun handleApiError(e: Exception) {
        // 默认实现，子类可以覆盖
    }
}