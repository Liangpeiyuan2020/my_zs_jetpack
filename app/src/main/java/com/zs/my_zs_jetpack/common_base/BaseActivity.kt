package com.zs.my_zs_jetpack.common_base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutId().let {
            binding = DataBindingUtil.setContentView(this, it)
        }
        initViewModal()
        init()
        observe()
    }

    abstract fun getLayoutId(): Int

    open fun observe() {
    }

    open fun initViewModal() {
    }

    open fun init() {}

}