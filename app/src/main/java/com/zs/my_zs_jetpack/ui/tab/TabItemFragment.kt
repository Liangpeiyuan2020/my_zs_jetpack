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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabItemFragment : LazyBaseFragment<FragmentTabItemBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_tab_item
    private val tabItemVm by viewModels<TabItemViewModel>()

    private lateinit var projectTabArticleAdapt: TabArticleAdapt
    private lateinit var accountTabArticleAdapt: ArticleAdapter

    private var type: Int = 0
    private var tabId: Int = 0

    override fun lazyInit() {

        type = arguments?.getInt("type") ?: 0
        tabId = arguments?.getInt("tabId") ?: 0
        tabItemVm?.loadData(tabId, type)
        initView()
        Log.i("TabFragment01", tabId.toString())
    }

    private fun initView() {
        //关闭更新动画
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.refreshLayout.setOnRefreshListener {
            Log.i("TabFragment1", tabId.toString())
            tabItemVm?.loadData(tabId, type)
            it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
            Log.i("TabFragment2", tabId.toString())
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
        if (type == 0) {
            projectTabArticleAdapt = TabArticleAdapt()
            binding.recyclerView.adapter = projectTabArticleAdapt
            binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    tabItemVm.projectTabItemArticles.collect {
                        projectTabArticleAdapt.submitData(it)
                    }
                }
            }
            observeLoadingState(projectTabArticleAdapt)
        } else {
            accountTabArticleAdapt = ArticleAdapter()
            binding.recyclerView.adapter = accountTabArticleAdapt
            binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    tabItemVm.accountTabItemArticles.collect {
                        accountTabArticleAdapt.submitData(it)
                    }
                }
            }
            observeLoadingState(accountTabArticleAdapt)
        }
    }
}