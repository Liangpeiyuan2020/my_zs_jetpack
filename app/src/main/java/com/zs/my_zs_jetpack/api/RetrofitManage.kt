package com.zs.my_zs_jetpack.api

class RetrofitManage {
    companion object {
        private val servicesMap = mutableMapOf<Class<*>, Any>()

        private val retrofit = RetrofitFactory.getFactory()

        fun <T : Any> getService(apiClass: Class<T>): T {
            //重入锁单例避免多线程安全问题
            return if (servicesMap[apiClass] == null) {
                synchronized(RetrofitManage::class.java) {
                    val t = retrofit.create(apiClass)
                    if (servicesMap[apiClass] == null) {
                        servicesMap[apiClass] = t
                    }
                    t
                }
            } else {
                servicesMap[apiClass] as T
            }
        }
    }
}