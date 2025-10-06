package com.zs.my_zs_jetpack.api

data class ApiResponse<T>(
    var data: T,
    var errorCode: Int,
    var errorMsg: String
)

