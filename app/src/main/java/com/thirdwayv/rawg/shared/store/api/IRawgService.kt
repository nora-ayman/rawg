package com.thirdwayv.rawg.shared.store.api

import com.thirdwayv.rawg.shared.constants.Constants
import com.thirdwayv.rawg.shared.store.models.response.GenreResponse
import com.thirdwayv.rawg.shared.store.models.response.ResponseWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IRawgService {

    @GET(Constants.GENRES)
    fun getGenres(@Query("page") page: Int, @Query("page_size") pageSize: Int): Single<ResponseWrapper<GenreResponse>>
}