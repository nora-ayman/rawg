package com.phoenix.rawg.shared.store.repositories

import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.api.datasource.remote.GamesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GamesRepository @Inject constructor(private val gamesApi: GamesApi,
                                          @IoDispatcher private val dispatcher: CoroutineDispatcher) {

    suspend fun getGames(
        page: Int,
        pageSize: Int = 20,
        genreIds: String
    ) = gamesApi.getGames(page, pageSize, genreIds)


    suspend fun getGameDetails(gameId: Int) = gamesApi.getGameDetails(gameId)


    suspend fun getGameTrailers(gameId: Int) = gamesApi.getGameTrailers(gameId)


    suspend fun getGameScreenshots(gameId: Int) = gamesApi.getGameScreenshots(gameId)

}