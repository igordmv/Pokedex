package com.globo.globosatplay.core

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    fun loadCircular(context: Context, url: String, imageView: ImageView, placeholder: Int? = null) {
        val options = RequestOptions().fitCenter().circleCrop()
        placeholder?.let { options.placeholder(it) }
        Glide.with(context).load(url).apply(options).into(imageView)
    }

    fun load(context: Context, url: String, placeholder: Int?, imageView: ImageView) {
        val options = RequestOptions().fitCenter()
        placeholder?.let { options.placeholder(it) }
        Glide.with(context).load(url).apply(options).into(imageView)
    }
}
