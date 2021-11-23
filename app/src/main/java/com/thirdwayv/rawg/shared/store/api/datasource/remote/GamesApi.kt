package com.thirdwayv.rawg.shared.store.api.datasource.remote

import com.thirdwayv.rawg.shared.store.api.IRawgService
import javax.inject.Inject

class GamesApi @Inject constructor(val client: IRawgService) {

    fun getGames(page: Int, pageSize: Int) = client.getGames(page, pageSize)

}