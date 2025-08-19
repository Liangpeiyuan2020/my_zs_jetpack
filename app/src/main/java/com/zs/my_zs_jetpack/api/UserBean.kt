package com.zs.my_zs_jetpack.api

data class UserBean(


    /**
     * admin : false
     * chapterTops : []
     * collectIds : [10479,12202,12148,10916,12175]
     * email :
     * icon :
     * id : 36628
     * nickname : 18616720137
     * password :
     * publicName : 18616720137
     * token :
     * type : 0
     * username : 18616720137
     */

    var admin: Boolean,
    var email: String?,
    var icon: String?,
    var id: Int,
    var nickname: String?,
    var password: String?,
    var publicName: String?,
    var token: String?,
    var type: Int,
    var username: String?,
    var chapterTops: List<*>?,
    var collectIds: List<Int>?,
)
