package com.zs.my_zs_jetpack.ui.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.ArticleAdapter
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentHomeBinding
import com.zs.my_zs_jetpack.api.Article
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : LazyBaseFragment<FragmentHomeBinding>() {
    private val homeVM: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var articleAdapt: ArticleAdapter
    override fun lazyInit() {
        intiView()
    }


    override fun observe() {

    }

    private fun intiView() {
        binding.vm = homeVM
        //关闭更新动画
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.refreshLayout.setOnRefreshListener {
            homeVM.refresh()
            it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        articleAdapt = ArticleAdapter(
            onCollectClick = { article: Article ->
                Log.i("onCollectClick", "${article.toString()}")
                homeVM.handleCollection(article.id, article.collect)
            }
        )
        binding.recyclerView.adapter = articleAdapt
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeVM.articles.collectLatest { pagingData ->
                    articleAdapt.submitData(pagingData)
                }
            }
        }
        // 启动状态监听
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeVM.collectionUpdates.collect { state ->
                    articleAdapt.updateAdaptCollectionState(state)

                }
            }
        }
        observeLoadingState(articleAdapt)
    }


    override fun getLayoutId(): Int = R.layout.fragment_home
}
