package com.thirdwayv.rawg.shared.store.models.response

import com.google.gson.annotations.SerializedName

class GameTrailerResponse {
     data class Base(val id: Int = 0,
                     val name: String = "",
                     val preview: String? = "",
                     @SerializedName("data")
                     val video: Data?)
     data class Data(
         @SerializedName("max")
         val videoUrl: String)
 }