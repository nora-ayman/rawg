package com.thirdwayv.rawg.shared.store.models.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class GameDetailsResponse (val id: Int = 0,
                                val name: String = "",
                                @SerializedName("background_image")
                                val thumbnail: String? = null,
                                val rating: Double,
                                val description: String,
                                val released: Date?)