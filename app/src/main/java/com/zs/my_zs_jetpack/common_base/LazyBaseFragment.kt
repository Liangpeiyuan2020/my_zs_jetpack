package com.zs.my_zs_jetpack.common_base

import androidx.databinding.ViewDataBinding

abstract class LazyBaseFragment<T : ViewDataBinding> : BaseFragment<T>() {
    override fun onResume() {
        super.onResume()
        lazyInit()
    }

    abstract fun lazyInit()
}