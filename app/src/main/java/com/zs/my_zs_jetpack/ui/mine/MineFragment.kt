package com.zs.my_zs_jetpack.ui.mine

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentMineBinding
import com.zs.my_zs_jetpack.event.LoginEvent
import com.zs.my_zs_jetpack.event.LogoutEvent
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : LazyBaseFragment<FragmentMineBinding>() {
    private val mineVm by viewModels<MineViewModel>()

    override fun init() {
        EventBus.getDefault().register(this)
    }

    override fun lazyInit() {
        binding.vm = mineVm
        mineVm.getInternalBean()
    }

    override fun onclick() {
        binding.tvName.clickNoRepeat {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
        binding.ivHead.clickNoRepeat {
//            mineVm.getInternalBean()
//            mineVm.resetIntegralBean()
        }
        binding.clSet.clickNoRepeat {
            val options = navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_settingsFragment,
                null,
                options
            )
        }
        binding.clCollect.clickNoRepeat {
            findNavController().navigate(R.id.action_mainFragment_to_collectFragment, Bundle().apply { putString("title", "我的收藏") })
        }
        binding.clArticle.clickNoRepeat {
            findNavController().navigate(R.id.action_mainFragment_to_categoryFragment, Bundle().apply { putString("title", "我的文章") })
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun loginEvent(loginEvent: LoginEvent) {
        mineVm.getInternalBean()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun logoutEvent(logoutEvent: LogoutEvent) {
        mineVm.resetIntegralBean()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

}