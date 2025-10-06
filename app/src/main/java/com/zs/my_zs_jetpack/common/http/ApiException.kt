package com.zs.my_zs_jetpack.common.http

/**
 * 用来封装业务错误信息
 *
 * @author zs
 * @date 2020-05-09
 */
class ApiException(val errorMessage: String, val errorCode: Int) :
    Throwable()