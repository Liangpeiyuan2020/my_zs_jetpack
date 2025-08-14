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
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentMainBinding
import com.zs.my_zs_jetpack.ui.home.HomeFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_main

    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() }
    private val mainVM: MainViewModel? by viewModels()


    override fun init() {
        binding.vm = mainVM
        fragmentList.apply {
            add(homeFragment)
        }
        val adapt = MyViewPageAdapt(requireActivity() as FragmentActivity, fragmentList)
        binding.viewPage.adapter = adapt
        binding.btnNav.setOnItemSelectedListener { item->
            when(item.itemId){
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