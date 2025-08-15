package com.zs.my_zs_jetpack.api

data class ApiResponse(
    var data: ApiPage,
    var errorCode: Int,
    var errorMsg: String
)

data class ApiPage(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: MutableList<Article>
)