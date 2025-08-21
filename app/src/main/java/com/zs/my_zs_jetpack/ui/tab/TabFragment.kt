package com.zs.my_zs_jetpack.ui.tab

import MyPagerAdapterBuilder
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.api.ArticleTab
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentTabBinding
import com.zs.my_zs_jetpack.commonAdapt.MyViewPageAdapt

class TabFragment : LazyBaseFragment<FragmentTabBinding>() {

    private val tabVm: TabViewModel by viewModels<TabViewModel>()

    //    private lateinit var viewPagerAdapter: MyViewPageAdapt
    private lateinit var viewPagerAdapter: FragmentStateAdapter
    private var type: Int = 0
    override fun lazyInit() {
        type = arguments?.getInt("type") ?: 0
        loadData(type)
        Log.i("TabFragment5", type.toString())
    }

    private fun loadData(type: Int) {
        tabVm.getTab(type)
    }

    override fun observe() {
        tabVm.articleTab.observe(viewLifecycleOwner) { it ->
            Log.i("TabFragment0", it!!.size.toString())
            initView(it)
        }
    }

    private fun initView(tabList: List<ArticleTab>) {
        viewPagerAdapter = MyPagerAdapterBuilder(requireActivity()).apply {
            tabList.forEach {
                addFragment(
                    TabItemFragment::class,
                    Bundle().apply {
                        //各个fragment传递信息
                        putInt("type", type)
                        putInt("tabId", it.id)
                        putString("name", it.name)
                    })
            }
        }.build()

        binding.vpArticleFragment.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.vpArticleFragment) { tab, position ->
            tab.text = tabList[position].name
        }.attach()
        binding.vpArticleFragment.offscreenPageLimit = 5
    }


    override fun getLayoutId(): Int = R.layout.fragment_tab


}