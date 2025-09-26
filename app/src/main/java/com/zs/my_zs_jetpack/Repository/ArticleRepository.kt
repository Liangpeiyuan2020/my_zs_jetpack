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
import com.zs.my_zs_jetpack.api.BannerBean
import com.zs.my_zs_jetpack.api.Collect
import com.zs.my_zs_jetpack.api.IntegralBean
import com.zs.my_zs_jetpack.api.NavigationBean
import com.zs.my_zs_jetpack.api.SystemBean
import com.zs.my_zs_jetpack.api.UserBean
import com.zs.my_zs_jetpack.paging.ArticlePagingSource
import com.zs.my_zs_jetpack.paging.CollectArticlePagingSource
import com.zs.my_zs_jetpack.paging.SearchPagingSource
import com.zs.my_zs_jetpack.paging.SystemListPagingSource
import com.zs.my_zs_jetpack.paging.TabAccountArticlePagingSource
import com.zs.my_zs_jetpack.paging.TabArticlePagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path
import retrofit2.http.Query

class ArticleRepository(val services: ApiServices) {
    fun search(keyWords: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { SearchPagingSource(services, keyWords) }
        ).flow
    }

    fun getCollectArticle(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { CollectArticlePagingSource(services) }
        ).flow
    }

    fun getSystemArticle(tableId: Int): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { SystemListPagingSource(services, tableId) }
        ).flow
    }

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
        Log.i("LoginFragment", dataList.toString())
        return dataList.data
    }

    suspend fun login(userName: String, userPassword: String): ApiResponse<UserBean> {
        val dataList = services.login(userName, userPassword)
        Log.i("LoginFragment", dataList.toString())
        return dataList
    }

    suspend fun register(
        userName: String,
        userPassword: String,
        rePassword: String
    ): ApiResponse<Any> {
        val dataList = services.register(userName, userPassword, rePassword)
        Log.i("LoginFragment", dataList.toString())
        return dataList
    }

    suspend fun logout(): ApiResponse<Any> {
        return services.logout()
    }

    suspend fun collect(id: Int): ApiResponse<Any> {
        return services.collect(id)
    }

    suspend fun unCollect(id: Int): ApiResponse<Any> {
        return services.unCollect(id)
    }

    suspend fun getBanner(): List<BannerBean> {
        return services.getBanner().data
    }
}