package com.zs.my_zs_jetpack


import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.zs.my_zs_jetpack.common_base.BaseActivity
import com.zs.my_zs_jetpack.databinding.ActivitySplashBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_splash
    private var timer: Job? = null
    override fun init() {
        timer = lifecycleScope.launch {
            delay(2000)
            startIntent()
        }
    }

    fun startIntent() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}