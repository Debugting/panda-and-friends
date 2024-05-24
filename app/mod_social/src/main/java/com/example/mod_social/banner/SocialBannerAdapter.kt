package com.example.mod_social.banner

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_frame.extensions.load
import com.youth.banner.adapter.BannerAdapter


class SocialBannerAdapter : BannerAdapter<Banner, SocialBannerAdapter.ViewHolder>(listOf()) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.setPadding(0, 0, 0, 0)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ViewHolder(imageView)
    }

    override fun onBindView(holder: ViewHolder, data: Banner, position: Int, size: Int) {
        holder.imageView.load(data.imgUrl)
    }

    class ViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}
