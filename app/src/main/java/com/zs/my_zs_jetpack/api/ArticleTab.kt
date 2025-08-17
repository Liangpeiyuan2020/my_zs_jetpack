package com.zs.my_zs_jetpack.api

class ArticleTab(
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTo: Boolean,
    var visible: Int,
    var children: List<*>,
)