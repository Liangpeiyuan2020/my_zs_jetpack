package com.zs.my_zs_jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_zs_jetpack.common_base.BaseActivity
import com.zs.my_zs_jetpack.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_main
}