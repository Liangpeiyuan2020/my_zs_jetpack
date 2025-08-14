package com.zs.my_zs_jetpack

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_zs_jetpack.common_base.BaseActivity
import com.zs.my_zs_jetpack.databinding.ActivityMainBinding
import com.zs.my_zs_jetpack.ui.main.MainFragment

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
}