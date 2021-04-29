package com.example.assignment.viewmodel

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.assignment.R


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(url: Int) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.placeholder_for_missing_posters)
        .into(this)
}