package com.zs.my_zs_jetpack.commonAdapt

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.youth.banner.adapter.BannerAdapter
import com.zs.my_zs_jetpack.R

import com.zs.my_zs_jetpack.api.BannerBean
import com.zs.my_zs_jetpack.ui.GlideRoundTransform

class MyBannerImageAdapter(dataList: List<BannerBean>) :
    BannerAdapter<BannerBean, MyBannerImageAdapter.ViewHolder>(dataList) {
    override fun onCreateHolder(
        parent: ViewGroup?,
        viewType: Int
    ): ViewHolder {
        val imageView = ImageView(parent?.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ViewHolder(imageView)
    }

    override fun onBindView(
        holder: ViewHolder,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        Glide.with(holder.imageView)
            .load(data?.imagePath)
            .centerCrop()
            .error(R.drawable.ic_launcher)
            .transition(withCrossFade())
            .transform(GlideRoundTransform(holder.itemView.context, 20))
            .into(holder.imageView)
    }

    inner class ViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}