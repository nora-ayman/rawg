package com.thirdwayv.rawg.shared.store.api

import com.thirdwayv.rawg.shared.constants.Constants
import com.thirdwayv.rawg.shared.store.models.response.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRawgService {

    @GET(Constants.GENRES)
    fun getGenres(@Query("page") page: Int, @Query("page_size") pageSize: Int): Single<ResponseWrapper<GenreResponse>>

    @GET(Constants.GAMES)
    fun getGames(@Query("page") page: Int, @Query("page_size") pageSize: Int, @Query("genres") genreIds: String): Single<ResponseWrapper<GameResponse>>

    @GET(Constants.GAME_DETAILS)
    fun getGameDetails(@Path("game_id") gameId: Int): Single<GameDetailsResponse>

    @GET(Constants.GAME_TRAILERS)
    fun getGameTrailers(@Path("game_id") gameId: Int): Single<ResponseWrapper<GameTrailerResponse>>
}