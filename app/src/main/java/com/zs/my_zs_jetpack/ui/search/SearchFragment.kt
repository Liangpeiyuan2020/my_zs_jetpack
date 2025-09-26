package com.zs.my_zs_jetpack.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.commonAdapt.ArticleAdapter
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchVm: SearchViewModel by viewModels()
    private lateinit var searchAdapter: ArticleAdapter
    override fun init() {
        super.init()
        initSearchBar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        searchAdapter = ArticleAdapter(
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
        observeLoadingState(searchAdapter)
    }

    private fun initSearchBar() {
        binding.searchBarProduct.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.length != 0) {
                    searchVm.search(query)
                } else {
                    Log.i("home2", query ?: "not")

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchVm.articles.collect {
                    searchAdapter.submitData(it)
                }
            }
        }
    }


    override fun getLayoutId() = R.layout.fragment_search


}