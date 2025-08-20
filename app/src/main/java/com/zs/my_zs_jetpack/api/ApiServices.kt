package com.zs.my_zs_jetpack.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    /**
     * 获取项目tab
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getAccountTabList(): ApiResponse<MutableList<ArticleTab>>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/list/{id}/{pageNum}/json")
    suspend fun getAccountList(@Path("id") cid: Int, @Path("pageNum") pageNum: Int)
            : ApiResponse<ArticlePage<Article>>

    /**
     * 体系
     */
    @GET("/tree/json")
    suspend fun getSystemList(): ApiResponse<MutableList<SystemBean>>

    /**
     * 导航
     */
    @GET("/navi/json")
    suspend fun getNavigation(): ApiResponse<MutableList<NavigationBean>>

    /**
     * 获取个人积分
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getIntegral(): ApiResponse<IntegralBean>


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<UserBean>

    /**
     * 注册
     */
    @POST("/user/register")
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): ApiResponse<Any>

    @GET("/user/logout/json")
    suspend fun logout():ApiResponse<Any>
}