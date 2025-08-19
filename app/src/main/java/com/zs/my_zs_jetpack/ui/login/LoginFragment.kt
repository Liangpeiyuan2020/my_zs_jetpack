package com.zs.my_zs_jetpack.ui.login

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentLoginBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import com.zs.my_zs_jetpack.utils.MyToast

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val loginVm by viewModels<LoginViewModel>()
    override fun init() {
        binding.vm = loginVm
    }

    override fun observe() {
        loginVm.username.observe(viewLifecycleOwner) {
            Log.i("LoginFragment0", it)
        }
        loginVm.password.observe(viewLifecycleOwner) {
            Log.i("LoginFragment1", it)
        }

    }

    override fun onclick() {
        binding.llLogin.clickNoRepeat {
            closeKeyboard(binding.etUsername, requireActivity())
            closeKeyboard(binding.etPassword, requireActivity())
            if (TextUtils.isEmpty(loginVm.username.value)) {
                MyToast.toast("请输入用户名")
                return@clickNoRepeat
            }
            if (TextUtils.isEmpty(loginVm.password.value)) {
                MyToast.toast("输入密码")
                return@clickNoRepeat
            }
            loginVm.login()
        }
    }

    fun closeKeyboard(mEditText: EditText, mContext: Context) {
        val imm =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

}