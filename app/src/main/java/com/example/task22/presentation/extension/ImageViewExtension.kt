package com.example.task22.presentation.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.task22.R

fun ImageView.loadImagesWithGlide(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}