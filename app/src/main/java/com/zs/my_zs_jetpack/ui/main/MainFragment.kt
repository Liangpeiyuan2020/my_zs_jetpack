package com.zs.my_zs_jetpack.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.MyViewPageAdapt
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentMainBinding
import com.zs.my_zs_jetpack.ui.home.HomeFragment
import com.zs.my_zs_jetpack.ui.mine.MineFragment
import com.zs.my_zs_jetpack.ui.square.SquareFragment
import com.zs.my_zs_jetpack.ui.tab.TabFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_main

    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy {
        HomeFragment()
    }
    private val projectFragment by lazy {
        TabFragment().apply {
            arguments = Bundle().apply { putInt("type", 0) }
        }
    }
    private val squareFragment by lazy {
        SquareFragment()
    }
    private val publishNumberFragment by lazy {
        TabFragment().apply {
            arguments = Bundle().apply { putInt("type", 1) }
        }
    }
    private val mineFragment by lazy {
        MineFragment()
    }
    private val mainVM: MainViewModel? by viewModels()


    override fun init() {
        binding.vm = mainVM
        fragmentList.apply {
            add(homeFragment)
            add(projectFragment)
            add(squareFragment)
            add(publishNumberFragment)
            add(mineFragment)
        }
        val adapt = MyViewPageAdapt(requireActivity(), fragmentList)
        binding.viewPage.adapter = adapt
//        binding.viewPage.offscreenPageLimit = fragmentList.size
        binding.btnNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    binding.viewPage.setCurrentItem(0, false)
                }

                R.id.menu_project -> binding.viewPage.setCurrentItem(1, false)
                R.id.menu_square -> binding.viewPage.setCurrentItem(2, false)
                R.id.menu_official_account -> binding.viewPage.setCurrentItem(3, false)
                R.id.menu_mine -> binding.viewPage.setCurrentItem(4, false)
            }
            true
        }

    }
}