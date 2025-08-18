package com.zs.my_zs_jetpack.ui.square

import androidx.fragment.app.viewModels
import androidx.lifecycle.map
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
    private lateinit var itemList: MutableList<SquareBaseBean>
    private var type: Int = 0

    override fun observe() {
        super.observe()
        systemVm.systemList.observe(viewLifecycleOwner) {
            it?.forEach {
                itemList.add(
                    SquareBaseBean(
                        it.name,
                        it.children.map { it.name }
                    )
                )
            }
        }
        systemVm.navigationList.observe(viewLifecycleOwner) {
            it?.forEach {
                itemList.add(
                    SquareBaseBean(
                        it.name,
                        it.articles.map { it.title }
                    )
                )
            }
        }
    }

    override fun lazyInit() {
        type = arguments?.getInt("index") ?: 0

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

        systemAdapter = SystemAdapter(itemList)
        binding.recyclerView.adapter = systemAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

    override fun getLayoutId(): Int = R.layout.fragment_system
}