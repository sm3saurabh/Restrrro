package com.restaurantfinder.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.restaurantfinder.R
import com.squareup.picasso.Picasso

@BindingAdapter("customBackgroundColor")
fun customBackgroundWithColor(view: View, customBackgroundColor: String) {

    view.setBackgroundResource(R.drawable.rounded_corner_bg)

    val bg = view.background as GradientDrawable

    bg.setColor(Color.parseColor("#$customBackgroundColor"))


}

@BindingAdapter("urlOfImage")
fun loadImageFromUrl(imageView: ImageView, urlOfImage: String) {
    if (urlOfImage.isNotBlank()) {
        Picasso.get().load(urlOfImage).into(imageView)
    } else {
        imageView.setImageResource(R.drawable.ic_location_on)
    }
}
