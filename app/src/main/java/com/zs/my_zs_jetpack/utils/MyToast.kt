package com.zs.my_zs_jetpack.utils

import android.text.TextUtils
import android.widget.Toast
import kotlin.time.Duration

object MyToast {
    fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        if (TextUtils.isEmpty(content)) return
        Toast.makeText(BaseApp.getContext(), content, duration).apply {
            show()
        }
    }
}