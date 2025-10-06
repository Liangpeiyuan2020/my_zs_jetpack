package com.zs.my_zs_jetpack.ui.square

import MyPagerAdapterBuilder
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.MyViewPageAdapt
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentSquareBinding

class SquareFragment : LazyBaseFragment<FragmentSquareBinding>() {

    //    private lateinit var viewPagerAdapter: MyViewPageAdapt
    private lateinit var viewPagerAdapter: FragmentStateAdapter
    override fun lazyInit() {
        mutableListOf<String>().apply {
            add("体系")
            add("系统")
            initViewPager(this)
        }
    }

    private fun initViewPager(tabList: MutableList<String>) {
//        val fragmentList = mutableListOf<Fragment>().apply {
//            tabList.forEachIndexed { index, item ->
//                add(SystemFragment().apply {
//                    arguments = Bundle().apply {
//                        putInt("index", index)
//                        putString("name", item)
//                    }
//                })
//            }
//        }
//
//        viewPagerAdapter = MyViewPageAdapt(requireActivity(), fragmentList)
//        binding.vpArticleFragment.adapter = viewPagerAdapter
//        binding.vpArticleFragment.offscreenPageLimit = fragmentList.size

        viewPagerAdapter = MyPagerAdapterBuilder(requireActivity()).apply {
            tabList.forEachIndexed { index, item ->
                addFragment(
                    SystemFragment::class, Bundle().apply {
                        putInt("index", index)
                        putString("name", item)
                    }
                )
            }
        }.build()
        binding.vpArticleFragment.adapter = viewPagerAdapter
        binding.vpArticleFragment.offscreenPageLimit = viewPagerAdapter.itemCount
        TabLayoutMediator(binding.tabLayout, binding.vpArticleFragment)
        { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    override fun getLayoutId(): Int = R.layout.fragment_square


}