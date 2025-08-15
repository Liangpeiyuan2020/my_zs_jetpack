package com.zs.my_zs_jetpack.Repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.paging.ArticlePagingSource
import kotlinx.coroutines.flow.Flow

class ArticleRepository(val services: ApiServices) {
    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { ArticlePagingSource(services) }
        ).flow
    }
}