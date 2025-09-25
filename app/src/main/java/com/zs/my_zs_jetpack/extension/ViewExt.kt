package com.zs.my_zs_jetpack.extension

import android.view.View
import com.zs.my_zs_jetpack.api.Article
import com.zs.my_zs_jetpack.api.Collect

var lastTime = 0L
fun View.clickNoRepeat(interval: Long = 400, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastTime != 0L && (currentTime - lastTime < interval)) {
            return@setOnClickListener
        }
        lastTime = currentTime
        onClick(it)
    }
}
fun Collect.toArticle(): Article {
    return Article(
        id = this.id,
        author = this.author,
        date = this.date,
        title = this.title,
        articleTag = this.articleTag,  // 注意：Collect 用的是 `chapterName`，Article 用的是 `superChapterName`
        collect = this.collect,
        link = this.link
    )
}