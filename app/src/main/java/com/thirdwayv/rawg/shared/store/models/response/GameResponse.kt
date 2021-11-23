package com.thirdwayv.rawg.shared.store.models.response

import com.google.gson.annotations.SerializedName

data class GameResponse (val id: Int = 0,
                         val name: String = "",
                         @SerializedName("background_image")
                         val thumbnail: String? = null,
                         val rating: Double)