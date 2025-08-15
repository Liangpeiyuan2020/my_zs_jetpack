package com.zs.my_zs_jetpack.api

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.AccessController.getContext
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object RetrofitFactory {
    private val BASE_URL: String = "https://www.wanandroid.com"
    private val DEFAULT_TIMEOUT = 10000
    private val okHttpClientBuilder: OkHttpClient.Builder
        get() {
            return OkHttpClient.Builder()
                .readTimeout(
                    DEFAULT_TIMEOUT.toLong(),
                    TimeUnit.MILLISECONDS
                )
                .connectTimeout(
                    DEFAULT_TIMEOUT.toLong(),
                    TimeUnit.MILLISECONDS
                )
                .addInterceptor(getLogInterceptor())

        }

    fun getFactory(): Retrofit {
        val okHttpClient = okHttpClientBuilder.build()
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    /**
     * 获取日志拦截器
     */
    private fun getLogInterceptor(): Interceptor {
        //http log 拦截器
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

//    /**
//     * 获取cookie持久化
//     */
//    private fun getCookie():ClearableCookieJar{
//        return PersistentCookieJar(
//            SetCookieCache(),
//            SharedPrefsCookiePersistor(getContext())
//        )
//    }

    /**
     * 获取缓存方式
     */
//    private fun getCache():Cache{
//        //缓存100Mb
//        return Cache( File(getContext().cacheDir, "cache")
//            , 1024 * 1024 * 100)
//    }
}