package com.zs.my_zs_jetpack.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiResponse
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.ArticlePage

class SystemListPagingSource(val service: ApiServices, val tableId: Int) :
    PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 0
            val response: ApiResponse<ArticlePage<Article>> =
                service.getSystemArticle(page, tableId)

            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (response.data.datas.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}