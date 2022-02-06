package com.phoenix.rawg.shared.store.api.datasource.remote

import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.api.IRawgService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GamesApi @Inject constructor(private val client: IRawgService, @IoDispatcher private val dispatcher: CoroutineDispatcher): BaseApi() {

    suspend fun getGames(page: Int, pageSize: Int, genreIds: String) =
        safeApiCall(dispatcher) { client.getGames(page, pageSize, genreIds) }

    suspend fun getGameDetails(gameId: Int) =
        safeApiCall(dispatcher) { client.getGameDetails(gameId) }


    suspend fun getGameTrailers(gameId: Int) =
        safeApiCall(dispatcher) { client.getGameTrailers(gameId) }


    suspend fun getGameScreenshots(gameId: Int) =
        safeApiCall(dispatcher) { client.getGameScreenshots(gameId) }


}