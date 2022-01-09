package com.phoenix.rawg.shared.store.api.datasource.remote

import com.phoenix.rawg.shared.store.api.IRawgService
import javax.inject.Inject

class GamesApi @Inject constructor(private val client: IRawgService) {

    suspend fun getGames(page: Int, pageSize: Int, genreIds: String) =
        client.getGames(page, pageSize, genreIds)

    suspend fun getGameDetails(gameId: Int) =
        client.getGameDetails(gameId)


    suspend fun getGameTrailers(gameId: Int) =
        client.getGameTrailers(gameId)


    suspend fun getGameScreenshots(gameId: Int) =
        client.getGameScreenshots(gameId)


}