package com.zs.my_zs_jetpack.commonAdapt

import android.widget.ImageView
import androidx.databinding.BindingAdapter
//import com.bumptech.glide.Glide
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

    /**
     * 加载网络圆角图片
     */
    @BindingAdapter(value = ["imgUrlRadius"])
    @JvmStatic
    fun imgUrlRadiusCircle(view: ImageView, url: String) {
//        view.loadRadius(view.context.applicationContext, url)
//        Glide.with(context)
//            .load(url)
//            .centerCrop()
//            .error(R.drawable.ic_launcher)
//            .transition(withCrossFade())
//            .transform(GlideRoundTransform(context,radius))
//            .into(view)
    }
}