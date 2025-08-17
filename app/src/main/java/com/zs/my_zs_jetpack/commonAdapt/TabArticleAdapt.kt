package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.databinding.ItemTabArticleBinding

class TabArticleAdapt: PagingDataAdapter<AllDataBean, TabArticleAdapt.TabArticleViewHolder>(Article_COMPARATOR) {

    companion object {
        private val Article_COMPARATOR = object : DiffUtil.ItemCallback<AllDataBean>() {
            override fun areItemsTheSame(oldItem: AllDataBean, newItem: AllDataBean): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AllDataBean, newItem: AllDataBean): Boolean =
                oldItem == newItem
        }
    }

    inner class TabArticleViewHolder(private val binding: ItemTabArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataBean: AllDataBean) {
            binding.dataBean = dataBean
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabArticleViewHolder {
        val binding = ItemTabArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TabArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabArticleViewHolder, position: Int) {
        getItem(position)?.let { article ->
            holder.bind(article)
        }
    }
}