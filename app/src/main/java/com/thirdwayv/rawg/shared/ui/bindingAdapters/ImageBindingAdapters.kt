package com.thirdwayv.rawg.shared.ui.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .transform(RoundedCorners(18))
        .into(view)

}