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
    override fun getLayoutId(): Int = R.layout.fragment_home
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
            homeVM?.refresh()
            it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        val articleList = mutableListOf<Article>()
//        articleList.apply {
//            add(Article(1, "top1", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(2, "top2", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(3, "top3", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(4, "top4", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(5, "top5", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(6, "top6", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(7, "top7", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(8, "top8", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(9, "top9", "auth1", "date1", "title1", "articleTag1", false))
//            add(Article(0, "top1", "auth1", "date1", "title1", "articleTag1", false))
//        }
//        Log.i("articleAdapt", articleList.size.toString())
        articleAdapt = ArticleAdapter()
        binding.recyclerView.adapter = articleAdapt
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeVM.articles.collectLatest { pagingData ->
                    articleAdapt.submitData(pagingData)
                }
            }
        }
    }


}