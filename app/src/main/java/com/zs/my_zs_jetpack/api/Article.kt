package com.zs.my_zs_jetpack.api

import com.google.gson.annotations.SerializedName

data class Article(
    var id: Int,
//    var topTitle: String,
    var author: String,
    @SerializedName("niceDate") var date: String,
    var title: String,
    @SerializedName("superChapterName") var articleTag: String,
    var collect: Boolean
)