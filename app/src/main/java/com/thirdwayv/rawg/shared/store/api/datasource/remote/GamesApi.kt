package com.thirdwayv.rawg.shared.store.api.datasource.remote

import com.thirdwayv.rawg.shared.store.api.IRawgService
import java.io.IOException
import javax.inject.Inject

class GamesApi @Inject constructor(val client: IRawgService) {

    suspend fun getGames(page: Int, pageSize: Int, genreIds: String) = client.getGames(page, pageSize, genreIds)

    fun getGameDetails(gameId: Int) = client.getGameDetails(gameId)

    fun getGameTrailers(gameId: Int) = client.getGameTrailers(gameId)

    fun getGameScreenshots(gameId: Int) = client.getGameScreenshots(gameId)

    suspend fun <T : Any> safeApiCall(call: suspend () -> com.thirdwayv.rawg.shared.network.Result<T>, exception: Exception): com.thirdwayv.rawg.shared.network.Result<T> = try {
        call.invoke()
    } catch (e: Exception) {
        com.thirdwayv.rawg.shared.network.Result.Error(exception)
    }

}