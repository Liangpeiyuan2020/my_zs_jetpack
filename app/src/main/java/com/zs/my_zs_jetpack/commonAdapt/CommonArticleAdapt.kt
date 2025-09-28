package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.CollectionState
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat

class CommonArticleAdapt(
    private val onCollectClick: (Article) -> Unit,
    private val onItemClick: (Article) -> Unit
) : RecyclerView.Adapter<CommonArticleAdapt.CommonArticleViewHolder>() {
    var articles: MutableList<Article> = mutableListOf()

    // 监听状态变化的关键类
    private val collectionStates = mutableMapOf<Int, CollectionState>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonArticleViewHolder {
        val binding = ItemHomeArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CommonArticleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CommonArticleViewHolder,
        position: Int
    ) {
        val article = articles[position]
        val currentState =
            collectionStates[article.id] ?: CollectionState(article.id, false, article.collect)
        holder.bind(article, currentState, position)
    }

    override fun getItemCount() = articles.size

    inner class CommonArticleViewHolder(val binding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, state: CollectionState, position: Int) {
            val displayArticle = article.copy(
                collect = state.isCollected
            )
            if (displayArticle.author.isEmpty() || displayArticle.author.isBlank())
                displayArticle.author = "鸿洋"

            binding.dataBean = displayArticle

            binding.isCollecting = state.isCollecting
            binding.ivCollect.clickNoRepeat {
                if (!state.isCollecting) {
                    onCollectClick(article)
                    notifyItemChanged(position)
                }
            }
            binding.root.clickNoRepeat {
                onItemClick(article)
            }
            binding.executePendingBindings()
        }
    }

    fun updateAdaptCollectionState(state: CollectionState) {
        // 1. 更新本地状态
        collectionStates[state.articleId] = state
    }

    fun submitData(list: MutableList<Article>) {
//        val oldSize = articles.size
//        articles.addAll(list)
//        notifyItemRangeInserted(oldSize, list.size)
        articles = list
        notifyDataSetChanged()
    }
}