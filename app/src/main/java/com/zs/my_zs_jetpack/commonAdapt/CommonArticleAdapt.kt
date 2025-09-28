package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.CollectionState
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat

class CommonArticleAdapt(
    private val onCollectClick: (Article) -> Unit,
    private val onItemClick: (Article) -> Unit
) : ListAdapter<Article, CommonArticleAdapt.CommonArticleViewHolder>(Article_COMPARATOR) {
    companion object {
        private val Article_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                //只有点赞和时间可能存在改变
                return oldItem.collect == newItem.collect
                        && oldItem.date == newItem.date
            }
        }
    }


    // 监听状态变化的关键类
    private val collectionStates = mutableMapOf<Int, CollectionState>()
    private var positionIndex = 0
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
        val article = getItem(position)
        val currentState =
            collectionStates[article.id] ?: CollectionState(article.id, false, article.collect)
        holder.bind(article, currentState, position)
    }


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
                    positionIndex = position
                    onCollectClick(article)

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
        notifyItemChanged(positionIndex)
    }

    /**
     * 重新加载数据时必须换一个list集合，否则diff不生效
     */
    override fun submitList(list: List<Article>?) {
        super.submitList(
            if (list == null) mutableListOf() else
                mutableListOf<Article>().apply {
                    addAll(
                        list
                    )
                })
    }

}