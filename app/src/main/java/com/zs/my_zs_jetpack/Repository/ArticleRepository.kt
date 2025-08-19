package com.zs.my_zs_jetpack.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zs.my_zs_jetpack.api.AllDataBean
import com.zs.my_zs_jetpack.api.ApiResponse
import com.zs.my_zs_jetpack.api.ApiServices
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.ArticleTab
import com.zs.my_zs_jetpack.api.IntegralBean
import com.zs.my_zs_jetpack.api.NavigationBean
import com.zs.my_zs_jetpack.api.SystemBean
import com.zs.my_zs_jetpack.api.UserBean
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

    suspend fun getSystemList(): List<SystemBean> {
        val dataList = services.getSystemList()
        return dataList.data
    }

    suspend fun getNavigationList(): List<NavigationBean> {
        val dataList = services.getNavigation()
        return dataList.data
    }

    suspend fun getInternal(): IntegralBean {
        val dataList = services.getIntegral()
        return dataList.data
    }

    suspend fun login(userName: String, userPassword: String): ApiResponse<UserBean> {
        val dataList = services.login(userName, userPassword)
        Log.i("LoginFragment", dataList.toString())
        return dataList
    }

    suspend fun register(userName: String, userPassword: String) {
        val dataList = services.register(userName, userPassword, userPassword)
    }
}