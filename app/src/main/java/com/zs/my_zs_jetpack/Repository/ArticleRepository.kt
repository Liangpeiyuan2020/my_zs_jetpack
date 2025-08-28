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
import com.zs.my_zs_jetpack.api.IntegralBean
import com.zs.my_zs_jetpack.api.NavigationBean
import com.zs.my_zs_jetpack.api.SystemBean
import com.zs.my_zs_jetpack.api.UserBean
import com.zs.my_zs_jetpack.paging.ArticlePagingSource
import com.zs.my_zs_jetpack.paging.TabAccountArticlePagingSource
import com.zs.my_zs_jetpack.paging.TabArticlePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ArticleRepository(val services: ApiServices) {
    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { ArticlePagingSource(services) }
        ).flow
    }

    suspend fun getArticleTab() = withContext(Dispatchers.IO) {
        services.getProjectTabList().data
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

    suspend fun getAccountTab() = withContext(Dispatchers.IO) {
        services.getAccountTabList().data
    }

    suspend fun getSystemList() = withContext(Dispatchers.IO) {
        services.getSystemList().data
    }

    suspend fun getNavigationList() = withContext(Dispatchers.IO) {
        services.getNavigation().data
    }

    suspend fun getInternal() = withContext(Dispatchers.IO) {
        services.getIntegral().data
    }

    suspend fun login(userName: String, userPassword: String) = withContext(Dispatchers.IO) {
        services.login(userName, userPassword)

    }

    suspend fun register(
        userName: String,
        userPassword: String,
        rePassword: String
    ) = withContext(Dispatchers.IO) {
        services.register(userName, userPassword, rePassword)

    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        services.logout()
    }

    suspend fun collect(id: Int) = withContext(Dispatchers.IO) {
        services.collect(id)
    }

    suspend fun unCollect(id: Int) = withContext(Dispatchers.IO) {
        services.unCollect(id)
    }

    suspend fun getBanner() = withContext(Dispatchers.IO) {
        services.getBanner().data
    }
}