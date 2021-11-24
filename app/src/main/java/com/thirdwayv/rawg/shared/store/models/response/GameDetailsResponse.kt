package com.thirdwayv.rawg.shared.store.models.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class GameDetailsResponse (val id: Int = 0,
                                val name: String = "",
                                val rating: Double,
                                val released: Date?,
                                @SerializedName("background_image")
                                val thumbnail: String? = null,
                                @SerializedName("description_raw")
                                val description: String,)