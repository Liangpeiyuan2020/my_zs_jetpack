package com.zs.my_zs_jetpack.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("/article/list/{page}/json")
    suspend fun getHomeList(@Path("page") pageNo: Int): ApiResponse<ArticlePage<Article>>

    @GET("/project/tree/json")
    suspend fun getProjectTabList(): ApiResponse<MutableList<ArticleTab>>

    /**
     * 获取项目列表
     */
    @GET("/project/list/{pageNum}/json")
    suspend fun getProjectList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : ApiResponse<ArticlePage<AllDataBean>>
}