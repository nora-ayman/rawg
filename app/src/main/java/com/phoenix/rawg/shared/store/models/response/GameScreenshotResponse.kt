package com.phoenix.rawg.shared.store.models.response

import com.google.gson.annotations.SerializedName

data class GameScreenshotResponse (val id: Int = 0,
                                   @SerializedName("image")
                                   val thumbnail: String? = null,
                                   val rating: Double)