package com.zs.my_zs_jetpack.ui.square

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.api.SquareBaseBean
import com.zs.my_zs_jetpack.commonAdapt.SystemAdapter
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentSystemBinding

class SystemFragment : LazyBaseFragment<FragmentSystemBinding>() {
    private val systemVm by viewModels<SquareViewModel>()
    private lateinit var systemAdapter: SystemAdapter
    private var type: Int = 0

    override fun observe() {
        super.observe()
        systemVm.itemList.observe(viewLifecycleOwner) {
            Log.i("SystemFragment", it?.size.toString())
            systemAdapter = SystemAdapter(it!!) { id, title ->
                findNavController().navigate(
                    R.id.action_mainFragment_to_categoryFragment,
                    Bundle().apply {
                        putString("title", title)
                        putInt("tableId", id)
                    })
            }
            binding.recyclerView.adapter = systemAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun lazyInit() {
        type = arguments?.getInt("index") ?: 0
        Log.i("SystemFragment", type.toString())
        loadData()
        initView()
    }

    private fun loadData() {
        if (type == 0) {
            systemVm.getSystemList()
        } else {
            systemVm.getNavigationList()
        }
    }

    private fun initView() {
        //关闭更新动画
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.refreshLayout.setOnRefreshListener {
            loadData()
            it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
//        binding.refreshLayout.setOnLoadMoreListener {
//            it.finishRefresh(1000)
//        }

    }

    override fun getLayoutId(): Int = R.layout.fragment_system
}