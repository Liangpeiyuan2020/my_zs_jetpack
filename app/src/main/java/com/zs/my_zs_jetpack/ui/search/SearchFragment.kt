package com.zs.my_zs_jetpack.ui.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.CommonArticleAdapt
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentSearchBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchVm: SearchViewModel by viewModels()
    private lateinit var searchAdapter: CommonArticleAdapt
    override fun init() {
        super.init()
        initSearchBar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        searchAdapter = CommonArticleAdapt(
            onCollectClick = { article ->
                searchVm.handleCollection(article.id, article.collect)
            },
            onItemClick = {
                findNavController().navigate(
                    R.id.action_searchFragment_to_webFragment,
                    Bundle().apply {
                        putString("title", it.title)
                        putString("loadUrl", it.link)
                    })
            }
        )
        binding.recyclerView.adapter = searchAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())


        binding.refreshLayout.setOnRefreshListener {
            searchVm.refresh()
            searchVm.clearStateCache()
            it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener {
            searchVm.loadMore()
            it.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
    }

    private fun initSearchBar() {
        binding.searchBarProduct.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length != 0) {
                    closeKeyboard(binding.searchBarProduct, requireActivity())
                    searchAdapter.submitList(emptyList())
                    searchVm.search1(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })

        binding.ivBack.clickNoRepeat {
            findNavController().navigateUp()
        }
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchVm.collectionUpdates.collect {
                    searchAdapter.updateAdaptCollectionState(it)
                }
            }
        }

        searchVm.articles1.observe(viewLifecycleOwner) {
            if (it.isEmpty()) binding.loadingTip.showEmpty()
            else binding.loadingTip.dismiss()
            searchAdapter.submitList(it)
        }

    }

    fun closeKeyboard(mEditText: SearchView, mContext: Context) {
        val imm =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun getLayoutId() = R.layout.fragment_search


}