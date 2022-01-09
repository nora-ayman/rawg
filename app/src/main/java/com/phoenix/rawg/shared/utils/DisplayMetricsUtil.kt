package com.phoenix.rawg.shared.utils

import android.content.Context
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class DisplayMetricsUtil @Inject constructor(val context: Context) {

    fun isScreenWidthCompact() = context.resources.displayMetrics.widthPixels <= 1080
    fun isScreenHeightCompact() = context.resources.displayMetrics.heightPixels <= 1500

    fun getRandomHeight(): Int {
        val displayHeight = context.resources.displayMetrics.heightPixels
        val factor = if(isScreenHeightCompact()) 200 else 350
        return ThreadLocalRandom.current()
            .nextInt((displayHeight - factor) / 3, (displayHeight - factor) / 2)
    }

}