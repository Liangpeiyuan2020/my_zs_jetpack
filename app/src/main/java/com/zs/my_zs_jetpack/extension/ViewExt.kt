package com.zs.my_zs_jetpack.extension

import android.view.View

var lastTime = 0L
fun View.clickNoRepeat(interval: Long = 400, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastTime != 0L && (currentTime - lastTime < interval)) {
            return@setOnClickListener
        }
        lastTime = currentTime
        onClick(it)
    }
}