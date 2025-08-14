package com.zs.my_zs_jetpack.ui.mine

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentMineBinding

class MineFragment : LazyBaseFragment<FragmentMineBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine


}