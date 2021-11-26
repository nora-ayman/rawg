package com.thirdwayv.rawg.shared.store.repositories

import com.thirdwayv.rawg.shared.di.IoDispatcher
import com.thirdwayv.rawg.shared.network.Result
import com.thirdwayv.rawg.shared.store.api.datasource.remote.GamesApi
import com.thirdwayv.rawg.shared.store.models.response.GameResponse
import com.thirdwayv.rawg.shared.store.models.response.ResponseWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(private val gamesApi: GamesApi,
                                          @IoDispatcher private val dispatcher: CoroutineDispatcher) {

    suspend fun getGames(page: Int, pageSize: Int = 20, genreIds: String): Result<ResponseWrapper<GameResponse>> =
        withContext(dispatcher) {
            gamesApi.safeApiCall(call = {
                gamesApi.getGames(page, pageSize, genreIds)
            }, exception = Exception())
            }

    fun getGameDetails(gameId: Int) = gamesApi.getGameDetails(gameId)

    fun getGameTrailers(gameId: Int) = gamesApi.getGameTrailers(gameId)

    fun getGameScreenshots(gameId: Int) = gamesApi.getGameScreenshots(gameId)

}