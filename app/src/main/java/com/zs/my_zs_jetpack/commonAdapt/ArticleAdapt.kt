package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.extension.clickNoRepeat
import com.zs.my_zs_jetpack.api.CollectionState

// ArticleAdapter.kt
class ArticleAdapter(
    private val onCollectClick: (Article) -> Unit,
    private val onItemClick: (Article) -> Unit
) :
    PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(Article_COMPARATOR) {

    // 监听状态变化的关键类
    private val collectionStates = mutableMapOf<Int, CollectionState>()

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

        fun bind(article: Article, state: CollectionState) {
            val displayArticle = article.copy(
                collect = state.isCollected
            )

            binding.dataBean = displayArticle

            binding.isCollecting = state.isCollecting
            binding.ivCollect.clickNoRepeat {
                if (!state.isCollecting) {
                    onCollectClick(article)
                }
            }
            binding.root.clickNoRepeat {
                onItemClick(article)
            }
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
            val currentState =
                collectionStates[article.id] ?: CollectionState(article.id, false, article.collect)
            holder.bind(article, currentState)
        }
    }

    // 局部更新方法
    fun updateAdaptCollectionState(state: CollectionState) {
        // 1. 更新本地状态
        collectionStates[state.articleId] = state

        // 2. 查找目标位置
        val position = findPositionById(state.articleId)

        // 3. 执行局部刷新
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position)
        }
    }

    // 查找文章位置
    private fun findPositionById(articleId: Int): Int {
        return snapshot().items.indexOfFirst { it.id == articleId }
    }
}