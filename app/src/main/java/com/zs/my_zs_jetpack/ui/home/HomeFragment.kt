package com.zs.my_zs_jetpack.ui.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.ArticleAdapter
import com.zs.my_zs_jetpack.common_base.LazyBaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentHomeBinding
import com.zs.my_zs_jetpack.model.Article

class HomeFragment : LazyBaseFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home
    private val homeVM: HomeViewModel? by viewModels<HomeViewModel>()
    private var articleAdapt: ArticleAdapter? = null


    override fun lazyInit() {
        intiView()
        loadData()
    }

    private fun loadData() {
    }

    private fun intiView() {
        binding.vm = homeVM
        //关闭更新动画
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.refreshLayout.setOnRefreshListener {
            loadData()
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
//            homeVM?.loadMoreArticle()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val articleList = mutableListOf<Article>()
        articleList.apply {
            add(Article(1,"top1", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(2,"top2", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(3,"top3", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(4,"top4", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(5,"top5", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(6,"top6", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(7,"top7", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(8,"top8", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(9,"top9", "auth1", "date1", "title1", "articleTag1", false))
            add(Article(0,"top1", "auth1", "date1", "title1", "articleTag1", false))
        }
        Log.i("articleAdapt", articleList.size.toString())
        articleAdapt = ArticleAdapter(articleList)
        binding.recyclerView.adapter = articleAdapt
    }


}