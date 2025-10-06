package com.zs.my_zs_jetpack.ui.collect

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.ArticleAdapter
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentCollectBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import kotlinx.coroutines.launch

class CollectFragment : BaseFragment<FragmentCollectBinding>() {
    //    private val title by lazy { arguments?.getString("title") }
    private val collectVm by viewModels<CollectViewModel>()
    private lateinit var title: String
    private lateinit var collectAdapter: ArticleAdapter

    override fun init() {
        super.init()
        title = arguments?.getString("title").toString()
        initView()
    }
    override fun onclick() {
        super.onclick()
        binding.ivBack.clickNoRepeat {
            findNavController().navigateUp()
        }
    }

    private fun initView() {
        binding.tvTitle.text = title
        collectAdapter = ArticleAdapter(
            onCollectClick = { article ->
                collectVm.handleCollection(article.id, article.collect)
            },
            onItemClick = {
                findNavController().navigate(
                    R.id.action_collectFragment_to_webFragment,
                    Bundle().apply {
                        putString("title", it.title)
                        putString("loadUrl", it.link)
                    })
            }
        )
        binding.recyclerView.adapter = collectAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        observeLoadingState(collectAdapter)
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectVm.collect.collect {
                        collectAdapter.submitData(it)
                    }
                }
                launch {
                    collectVm.collectionUpdates.collect {
                        collectAdapter.updateAdaptCollectionState(it)
                    }
                }
            }
        }

    }

    override fun getLayoutId() = R.layout.fragment_collect

//    override fun observe() {
//        super.observe()
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                collectVm.collect.collect {
//
//                }
//            }
//        }
//    }

}