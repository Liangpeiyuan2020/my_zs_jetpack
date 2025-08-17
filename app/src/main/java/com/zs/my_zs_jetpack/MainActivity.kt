package com.zs.my_zs_jetpack

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zs.my_zs_jetpack.AppGlobals.GlobalLoadingState
import com.zs.my_zs_jetpack.common_base.BaseActivity
import com.zs.my_zs_jetpack.databinding.ActivityMainBinding
import com.zs.my_zs_jetpack.ui.main.MainFragment
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun onBackPressed() {
        val mMainNavFragment: androidx.fragment.app.Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        //获取当前所在的fragment
        val fragment =
            mMainNavFragment?.childFragmentManager?.primaryNavigationFragment
        //如果当前处于根fragment即HostFragment
        if (fragment is MainFragment) {
            //Activity退出但不销毁
            moveTaskToBack(false)
        } else {
            super.onBackPressed()
        }
    }

    override fun init() {
        setupLoadingOverlay()
    }

    private fun setupLoadingOverlay() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                AppGlobals.loadingState.collect { state ->
                    when (state) {
                        GlobalLoadingState.LOADING -> showGlobalLoading()
                        GlobalLoadingState.PAGING_LOADING_MORE -> showPagingLoadingMore()
                        else -> hideGlobalLoading()
                    }
                }
            }
        }
    }

    private fun showGlobalLoading() {
        Log.i("showGlobalLoading", "showGlobalLoading")
        binding.loadingTip.loading()
//        binding.globalOverlay.visibility = View.VISIBLE
//        binding.globalProgress.visibility = View.VISIBLE
//
//        // 禁用用户交互
//        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//
//        // 启用加载动画
//        binding.globalProgress.isIndeterminate = true
    }

    private fun hideGlobalLoading() {
        Log.i("showGlobalLoading", "hideGlobalLoading")
        binding.loadingTip.dismiss()
//        binding.globalOverlay.visibility = View.GONE
//        binding.globalProgress.visibility = View.GONE
//
//        // 恢复用户交互
//        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun showPagingLoadingMore() {
        // 分页加载不显示全局遮罩，只显示底部指示器
        // (在Fragment内部处理)
    }
}