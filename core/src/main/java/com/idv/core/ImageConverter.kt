package com.globo.globosatplay.core

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageConverter {

    fun load(
        context: Context, url: String, imageView: ImageView, width: Int?, height: Int?
    ) {
        val options = RequestOptions().apply {
            centerCrop()
            priority(Priority.HIGH)
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            if (width != null && height != null) {
                override(width, height)
            }
        }
        try {
            Glide.with(context).load(url).apply(options).into(imageView)
        } catch (error: OutOfMemoryError) {
            imageView.measure(0, 0)
            options.apply {
                override(imageView.measuredWidth, imageView.maxHeight)
            }
            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }

    fun loadNoCache(context: Context, url: String, placeholder: Int?, imageView: ImageView) {

        val options = RequestOptions().apply {
            centerCrop()
            priority(Priority.HIGH)
            diskCacheStrategy(DiskCacheStrategy.NONE)
            placeholder?.let { placeholder(it) }
        }
        try {
            Glide.with(context).load(url).apply(options).into(imageView)
        } catch (error: OutOfMemoryError) {
            imageView.measure(0, 0)
            options.apply {
                override(imageView.measuredWidth, imageView.maxHeight)
            }
            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }

    fun loadCircleCrop(context: Context, url: String, placeholder: Int?, imageView: ImageView) {

        val options = RequestOptions().apply {
            circleCrop()
            priority(Priority.HIGH)
            diskCacheStrategy(DiskCacheStrategy.NONE)
            placeholder?.let { placeholder(it) }
        }

        try {
            Glide.with(context).load(url).apply(options).into(imageView)
        } catch (error: OutOfMemoryError) {
            imageView.measure(0, 0)
            options.apply {
                override(imageView.measuredWidth, imageView.maxHeight)
            }
            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }
}