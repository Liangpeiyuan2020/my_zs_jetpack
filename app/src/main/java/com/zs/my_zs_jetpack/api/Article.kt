package com.zs.my_zs_jetpack.api

data class Article(
    var id: Int,
    var topTitle: String,
    var author: String,
    var date: String,
    var title: String,
    var articleTag: String,
    var collect: Boolean
)