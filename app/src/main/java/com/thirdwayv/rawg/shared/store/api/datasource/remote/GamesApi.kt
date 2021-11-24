package com.thirdwayv.rawg.shared.store.api.datasource.remote

import com.thirdwayv.rawg.shared.store.api.IRawgService
import javax.inject.Inject

class GamesApi @Inject constructor(val client: IRawgService) {

    fun getGames(page: Int, pageSize: Int, genreIds: String) = client.getGames(page, pageSize, genreIds)

    fun getGameDetails(gameId: Int) = client.getGameDetails(gameId)

    fun getGameTrailers(gameId: Int) = client.getGameTrailers(gameId).map { it.results.first() }

}