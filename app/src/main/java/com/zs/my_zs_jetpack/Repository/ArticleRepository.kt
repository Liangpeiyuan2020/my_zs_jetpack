package com.zs.my_zs_jetpack.Repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiResponse
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.ArticleTab
import com.zs.my_zs_jetpack.paging.ArticlePagingSource
import com.zs.my_zs_jetpack.paging.TabAccountArticlePagingSource
import com.zs.my_zs_jetpack.paging.TabArticlePagingSource
import kotlinx.coroutines.flow.Flow

class ArticleRepository(val services: ApiServices) {
    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { ArticlePagingSource(services) }
        ).flow
    }

    suspend fun getArticleTab(): List<ArticleTab> {
        val dataList = services.getProjectTabList()
        return dataList.data
    }

    fun getProjectArticleList(tableId: Int): Flow<PagingData<AllDataBean>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { TabArticlePagingSource(services, tableId) }
        ).flow
    }

    fun getAccountArticleList(tableId: Int): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { TabAccountArticlePagingSource(services, tableId) }
        ).flow
    }

    suspend fun getAccountTab(): List<ArticleTab> {
        val dataList = services.getAccountTabList()
        return dataList.data
    }
}