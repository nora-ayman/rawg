package com.phoenix.rawg.shared.store.api

import com.phoenix.rawg.shared.constants.Constants
import com.phoenix.rawg.shared.network.ResultWrapper
import com.phoenix.rawg.shared.store.models.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRawgService {

    @GET(Constants.GENRES)
    suspend fun getGenres(@Query("page") page: Int, @Query("page_size") pageSize: Int): ResponseWrapper<GenreResponse>

    @GET(Constants.GAMES)
    suspend fun getGames(@Query("page") page: Int, @Query("page_size") pageSize: Int, @Query("genres") genreIds: String): ResponseWrapper<GameResponse>

    @GET(Constants.GAME_DETAILS)
    suspend fun getGameDetails(@Path("game_id") gameId: Int): ResponseWrapper<GameDetailsResponse>

    @GET(Constants.GAME_TRAILERS)
    suspend fun getGameTrailers(@Path("game_id") gameId: Int): ResponseWrapper<GameTrailerResponse.Base>

    @GET(Constants.GAME_SCREENSHOTS)
    suspend fun getGameScreenshots(@Path("game_id") gameId: Int): ResponseWrapper<GameScreenshotResponse>
}