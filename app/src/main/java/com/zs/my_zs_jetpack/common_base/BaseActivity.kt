package com.zs.my_zs_jetpack.common_base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutId().let{
//            setContentView(it)
            binding = DataBindingUtil.setContentView(this, it)
        }
        initViewModal()
        observe()
    }

    open fun observe() {
    }

    open fun initViewModal() {
    }
    abstract fun getLayoutId(): Int
}