package com.zs.my_zs_jetpack.ui.tab


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.ArticleAdapter
import com.zs.my_zs_jetpack.commonAdapt.TabArticleAdapt
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentTabItemBinding
import com.zs.my_zs_jetpack.paging.TabAccountArticlePagingSource
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TabItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountTabItemFragment : LazyBaseFragment<FragmentTabItemBinding>() {
    private val accountVm by viewModels<AccountViewModel>()

    private lateinit var accountTabArticleAdapt: ArticleAdapter

    private var tabId: Int = 0

    override fun lazyInit() {
        tabId = arguments?.getInt("tabId") ?: 0
        accountVm.loadData(tabId)
        initView()
    }

    private fun initView() {
        //关闭更新动画
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.refreshLayout.setOnRefreshListener {
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }

        accountTabArticleAdapt = ArticleAdapter(
            onCollectClick = { article ->
                val shouldCollect = !article.collect
                accountVm.handleCollection(article.id, shouldCollect)
            }
        )
        binding.recyclerView.adapter = accountTabArticleAdapt
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountVm.accountTabItemArticles.collect {
                    accountTabArticleAdapt.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountVm.collectionUpdates
            }
        }
        observeLoadingState(accountTabArticleAdapt)

    }

    override fun getLayoutId(): Int = R.layout.fragment_tab_item
}