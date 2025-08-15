package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.databinding.ItemHomeArticleBinding
import com.zs.my_zs_jetpack.model.Article

class ArticleAdapter(
    private val articleList: List<Article>,
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val binding =
            ItemHomeArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {

        holder.bind(articleList[position])

    }

    override fun getItemCount(): Int = articleList.size

    inner class ArticleViewHolder(private val binding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.dataBean = article
            binding.executePendingBindings()
        }
    }
}