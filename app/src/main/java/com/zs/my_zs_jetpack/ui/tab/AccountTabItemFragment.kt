package com.zs.my_zs_jetpack.ui.tab


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.ArticleAdapter

import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentTabItemBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TabItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountTabItemFragment : LazyBaseFragment<FragmentTabItemBinding>() {
    private val accountVm by viewModels<AccountViewModel>()

    private lateinit var accountAdapt: ArticleAdapter

    private var tabId: Int = 0

    override fun lazyInit() {
        tabId = arguments?.getInt("tabId") ?: 0
        accountVm.loadData(tabId)
        initView()
    }

    private fun initView() {


        accountAdapt = ArticleAdapter(
            onCollectClick = { article ->
                accountVm.handleCollection(article.id, article.collect)
            }
        )
        binding.recyclerView.adapter = accountAdapt
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountVm.accountTabItemArticles.collect {
                    accountAdapt.submitData(it)
                }
            }
        }
        observeLoadingState(accountAdapt)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountVm.collectionUpdates.collect { state ->
                    accountAdapt.updateAdaptCollectionState(state)
                }
            }
        }
        //关闭更新动画
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.refreshLayout.setOnRefreshListener {
            accountAdapt.refresh()
            accountVm.clearStateCache()
            it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_tab_item
}