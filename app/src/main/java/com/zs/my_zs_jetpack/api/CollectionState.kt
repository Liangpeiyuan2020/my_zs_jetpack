package com.zs.my_zs_jetpack.api

data class CollectionState(
    val articleId: Int,
    val isCollecting: Boolean, // 当前是否正在操作中
    val isCollected: Boolean   // 目标状态（成功后的状态）
)