package com.zs.my_zs_jetpack.common_base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.zs.my_zs_jetpack.AppGlobals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

    open fun observeLoadingState(adapter: PagingDataAdapter<*, *>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            // 只在初始加载时显示全局遮罩
                            AppGlobals.showGlobalLoading()
                        }

                        is LoadState.NotLoading,
                        is LoadState.Error -> {
                            // 初始加载完成（无论成功失败）
                            AppGlobals.hideGlobalLoading()


                        }
                    }
                }
            }
        }
    }
}
