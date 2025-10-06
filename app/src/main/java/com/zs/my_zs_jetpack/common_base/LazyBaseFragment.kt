package com.zs.my_zs_jetpack.common_base

import androidx.databinding.ViewDataBinding

abstract class LazyBaseFragment<T : ViewDataBinding> : BaseFragment<T>() {
    private var isFirstResume = true
    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            lazyInit()
        }
    }

    abstract fun lazyInit()
    override fun onDestroy() {
        super.onDestroy()
        isFirstResume = true
    }
}