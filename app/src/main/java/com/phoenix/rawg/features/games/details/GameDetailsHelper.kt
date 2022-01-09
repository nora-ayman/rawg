package com.phoenix.rawg.features.games.details

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.phoenix.rawg.R
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GameDetailsHelper @Inject constructor(val context: Context) {

    fun getReleaseDate(date: Date?): SpannableStringBuilder? {
        if (date == null)
            return SpannableStringBuilder().append("N/A")
        val formattedDate = SimpleDateFormat("EEEE dd, YYYY").format(date)
        val spannableStringBuilder = SpannableStringBuilder()
            .append(context.resources.getString(R.string.release_date_header) + ": ")
            .append(formattedDate)

        spannableStringBuilder.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorOnSecondary)),
            0,
            context.resources.getString(R.string.release_date_header).length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableStringBuilder.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)),
            context.resources.getString(R.string.release_date_header).length + 2,
            context.resources.getString(R.string.release_date_header).length + formattedDate.length + 2,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableStringBuilder
    }
}