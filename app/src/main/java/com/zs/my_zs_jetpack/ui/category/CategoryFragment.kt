package com.zs.my_zs_jetpack.ui.category

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.TabArticleAdapt
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentCategoryBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import kotlinx.coroutines.launch

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val categoryVm: CategoryViewModel by viewModels()
    private lateinit var categoryAdapt: TabArticleAdapt
    private var tableId: Int = 0
    private lateinit var title: String

    override fun init() {
        super.init()
        tableId = arguments?.getInt("tableId") ?: 0
        title = arguments?.getString("title").toString()
        initView()
        loadData()
    }

    override fun onclick() {
        super.onclick()
        binding.ivBack.clickNoRepeat {
            findNavController().navigateUp()
        }
    }

    private fun loadData() {
        categoryVm.loadData(tableId)
    }


    private fun initView() {
        binding.tvTitle.text = title
        categoryAdapt = TabArticleAdapt(
            onCollectClick = { article ->
//                projectVm.handleCollection(article.id, article.collect)
            },
            onItemClick = {
                findNavController().navigate(
                    R.id.action_mainFragment_to_webFragment,
                    Bundle().apply {
                        putString("title", it.title)
                        putString("loadUrl", it.link)
                    })
            }
        )
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryVm.articles.collect {
                    categoryAdapt.submitData(it)
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_category
}