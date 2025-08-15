package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.api.Article
// ArticleAdapter.kt
class ArticleAdapter : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(Article_COMPARATOR) {

    companion object {
        private val Article_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

    inner class ArticleViewHolder(private val binding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.dataBean = article
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemHomeArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { article ->
            holder.bind(article)
        }
    }
}