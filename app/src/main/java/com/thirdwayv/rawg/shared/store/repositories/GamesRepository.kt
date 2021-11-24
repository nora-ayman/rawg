package com.thirdwayv.rawg.shared.store.repositories

import com.thirdwayv.rawg.shared.store.api.datasource.remote.GamesApi
import javax.inject.Inject

class GamesRepository @Inject constructor(private val gamesApi: GamesApi) {

    fun getGames(page: Int, pageSize: Int = 20, genreIds: String) = gamesApi.getGames(page, pageSize, genreIds)

    fun getGameDetails(gameId: Int) = gamesApi.getGameDetails(gameId)

    fun getGameTrailers(gameId: Int) = gamesApi.getGameTrailers(gameId)

    fun getGameScreenshots(gameId: Int) = gamesApi.getGameScreenshots(gameId)

}