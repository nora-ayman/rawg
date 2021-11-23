package com.thirdwayv.rawg.shared.store.models.response

import com.google.gson.annotations.SerializedName

data class GenreResponse (val id: Int = 0,
                          val name: String = "",
                          @SerializedName("image_background")
                          val thumbnail: String? = null)