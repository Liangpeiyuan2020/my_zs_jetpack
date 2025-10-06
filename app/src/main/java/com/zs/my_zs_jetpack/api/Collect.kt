package com.zs.my_zs_jetpack.api

import com.google.gson.annotations.SerializedName

data class Collect(
    @SerializedName("originId") var id: Int,
//    var topTitle: String,
    var author: String,
    @SerializedName("niceDate") var date: String,
    var title: String,
    @SerializedName("chapterName") var articleTag: String,
    var collect: Boolean,
    var link: String
)