package com.phoenix.rawg.shared.ui.bindingAdapters

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
        .fitCenter()
        .transform(GranularRoundedCorners(18f, 18f, 0f, 0f))
        .into(view)

}


@BindingAdapter("imageUrlOneAndOneCorners")
fun setImageWithOneAndOneCorners(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .fitCenter()
        .transform(GranularRoundedCorners(0f, 9f, 0f, 9f))
        .into(view)

}


@BindingAdapter("imageUrlStartRoundedCorners")
fun setImageWithStartRoundedCorners(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .fitCenter()
        .transform(GranularRoundedCorners(18f, 0f, 0f, 18f))
        .into(view)

}

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .into(view)

}