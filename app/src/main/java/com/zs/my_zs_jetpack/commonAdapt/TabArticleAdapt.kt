package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.CollectionState
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.databinding.ItemTabArticleBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat

class TabArticleAdapt(
    private val onCollectClick: (AllDataBean) -> Unit,
    private val onItemClick: (AllDataBean) -> Unit
) :
    PagingDataAdapter<AllDataBean, TabArticleAdapt.TabArticleViewHolder>(Article_COMPARATOR) {
    val collectionStates = mutableMapOf<Int, CollectionState>()

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

        fun bind(dataBean: AllDataBean, state: CollectionState) {
            val newDateBean = dataBean.copy(collect = state.isCollected)
            binding.dataBean = newDateBean
            binding.ivCollect.clickNoRepeat {
                if (!state.isCollecting) {
                    onCollectClick(newDateBean)
                }
            }
            binding.root.clickNoRepeat {
                onItemClick(newDateBean)
            }
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
            val currentState =
                collectionStates[article.id] ?: CollectionState(article.id, false, article.collect)
            holder.bind(article, currentState)
        }
    }

    fun updateAdaptCollectionState(state: CollectionState) {
        collectionStates[state.articleId] = state
        val position = findPositionById(state)

        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position)
        }

    }

    private fun findPositionById(state: CollectionState): Int {
        return snapshot().items.indexOfFirst { it.id == state.articleId }
    }
}