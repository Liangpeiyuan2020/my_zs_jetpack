package com.zs.my_zs_jetpack.api

data class ArticlePage<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: MutableList<T>
)