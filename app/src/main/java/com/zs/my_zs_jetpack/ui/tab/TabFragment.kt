package com.zs.my_zs_jetpack.ui.tab

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.map
import com.google.android.material.tabs.TabLayoutMediator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.api.ArticleTab
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentTabBinding
import com.zs.my_zs_jetpack.ui.main.MyViewPageAdapt

class TabFragment : LazyBaseFragment<FragmentTabBinding>() {

    private val tabVm: TabViewModel by viewModels<TabViewModel>()
    private lateinit var viewPagerAdapter: MyViewPageAdapt
    override fun lazyInit() {
        loadData()
    }

    private fun loadData() {
        tabVm.getTab()
    }

    override fun observe() {
        tabVm.articleTab.observe(viewLifecycleOwner) { it ->
            Log.i("TabFragment0", it!!.size.toString())
            initView(it)
        }
    }

    private fun initView(tabList: List<ArticleTab>) {
        val fragmentList = arrayListOf<Fragment>().apply {
            tabList.forEach {
                add(TabItemFragment().apply {
                    //想各个fragment传递信息
                    val bundle = Bundle()
                    bundle.putInt("type", 0)
                    bundle.putInt("tabId", it.id)
                    bundle.putString("name", it.name)
                    arguments = bundle
                })
            }
        }
        viewPagerAdapter = MyViewPageAdapt(requireActivity(), fragmentList)

        binding.vpArticleFragment.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.vpArticleFragment) { tab, position ->
            tab.text = tabList[position].name
        }.attach()
        binding.vpArticleFragment.offscreenPageLimit = 5
    }


    override fun getLayoutId(): Int = R.layout.fragment_tab


}