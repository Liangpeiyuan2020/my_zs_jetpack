package com.zs.my_zs_jetpack.ui.login

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentRegisterBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import com.zs.my_zs_jetpack.utils.MyToast

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {


    private val viewModel: RegisterViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
    }


    override fun observe() {
        viewModel.registerLiveData.observe(viewLifecycleOwner) {
            MyToast.toast("注册成功")
            findNavController().navigateUp()
        }
    }

    override fun onclick() {
        binding.rlRegister.clickNoRepeat {
            closeKeyboard(binding.etUsername, requireActivity())
            closeKeyboard(binding.etPassword, requireActivity())
            closeKeyboard(binding.etRePassword, requireActivity())
            if (TextUtils.isEmpty(viewModel.username.value)) {
                MyToast.toast("请输入用户名")
                return@clickNoRepeat
            }
            if (TextUtils.isEmpty(viewModel.password.value)) {
                MyToast.toast("输入密码")
                return@clickNoRepeat
            }
            if (TextUtils.isEmpty(viewModel.rePassword.value)) {
                MyToast.toast("请输入确认密码")
                return@clickNoRepeat
            }
            viewModel.register()
        }
        binding.ivBack.clickNoRepeat {
            findNavController().navigateUp()
        }
        binding.ivClear.clickNoRepeat {
            viewModel.username.value = ""
        }
        binding.ivPasswordVisibility.clickNoRepeat {
            viewModel.passIsVisibility.value = !viewModel.passIsVisibility.value!!
        }
        binding.ivRePasswordVisibility.clickNoRepeat {
            viewModel.rePassIsVisibility.value = !viewModel.rePassIsVisibility.value!!
        }
    }

    fun closeKeyboard(mEditText: EditText, mContext: Context) {
        val imm =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun getLayoutId(): Int = R.layout.fragment_register
}