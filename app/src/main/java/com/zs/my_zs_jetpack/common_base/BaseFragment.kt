package com.zs.my_zs_jetpack.common_base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    protected lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragmentVM()
    }

    open fun initFragmentVM() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initActivityVM()
    }

    open fun initActivityVM() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getLayoutId().let {
            binding = DataBindingUtil.inflate<T>(inflater, it, container, false)
            binding.lifecycleOwner = viewLifecycleOwner
            return binding.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
        onclick()

    }


    open fun onclick() {}

    open fun init() {}

    open fun observe() {}

    abstract fun getLayoutId(): Int
}
