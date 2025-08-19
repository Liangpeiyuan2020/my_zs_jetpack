package com.zs.my_zs_jetpack.ui.mine

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentMineBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat

class MineFragment : LazyBaseFragment<FragmentMineBinding>() {
    private val mineVm by viewModels<MineViewModel>()
    override fun lazyInit() {
        binding.vm = mineVm
        mineVm.getInternalBean()
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine
    override fun onclick() {
        binding.tvName.clickNoRepeat {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

    }

}