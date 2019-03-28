package com.goldenpiedevs.schedule.app.core.utils.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.VERBOSE)
        builder.setDefaultRequestOptions(RequestOptions().placeholder(CircularProgressDrawable(context)
                .apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                }))
                .setDefaultTransitionOptions(Drawable::class.java, DrawableTransitionOptions.withCrossFade())
    }
}