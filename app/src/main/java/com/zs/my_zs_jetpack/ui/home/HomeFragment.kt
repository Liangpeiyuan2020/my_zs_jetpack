package com.zs.my_zs_jetpack.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentHomeBinding

class HomeFragment : LazyBaseFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun lazyInit() {
    }


}