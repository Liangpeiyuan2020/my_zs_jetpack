package com.zs.my_zs_jetpack.commonAdapt

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.zs.my_zs_jetpack.R

object ArticleCollectIconAdapter {
    @BindingAdapter(value = ["articleCollect"])
    @JvmStatic
    fun imgPlayBlur(view: ImageView, collect: Boolean) {
        if (collect) {
            view.setImageResource(R.mipmap._collect)
        } else {
            view.setImageResource(R.mipmap.un_collect)
        }
    }
}