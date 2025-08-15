package com.zs.my_zs_jetpack.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("/article/list/{page}/json")
    suspend fun getHomeList(@Path("page") pageNo: Int): ApiResponse
}