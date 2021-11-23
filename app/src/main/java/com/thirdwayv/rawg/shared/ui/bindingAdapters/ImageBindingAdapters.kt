package com.thirdwayv.rawg.shared.ui.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


@BindingAdapter("imageUrlRoundedCorners")
fun setImageWithRoundedCorners(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .transform(RoundedCorners(18))
        .into(view)

}

@BindingAdapter("imageUrlTopRoundedCorners")
fun setImageWithTopRoundedCorners(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .centerInside()
        .transform(GranularRoundedCorners(18f, 18f, 0f, 0f))
        .into(view)

}