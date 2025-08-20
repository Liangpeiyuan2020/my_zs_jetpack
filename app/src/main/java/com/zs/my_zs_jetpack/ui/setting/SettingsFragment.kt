package com.zs.my_zs_jetpack.ui.setting

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentSettingsBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import com.zs.my_zs_jetpack.utils.MyToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private val settingsVm by viewModels<SettingsViewModel>()
    override fun getLayoutId(): Int = R.layout.fragment_settings
    override fun observe() {
        settingsVm.logoutLiveData.observe(viewLifecycleOwner) {
            Log.i("logoutLiveData", it.toString())
            MyToast.toast("退出登录成功")
            viewLifecycleOwner.lifecycleScope.launch {
                findNavController().navigateUp()
            }

        }
    }

    override fun onclick() {
        binding.tvLogout.clickNoRepeat {
            settingsVm.logout()
        }
    }

}