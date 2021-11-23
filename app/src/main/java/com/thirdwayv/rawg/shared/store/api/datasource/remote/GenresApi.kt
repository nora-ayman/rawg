package com.thirdwayv.rawg.shared.store.api.datasource.remote

import com.thirdwayv.rawg.shared.store.api.IRawgService
import javax.inject.Inject

class GenresApi @Inject constructor(private val client: IRawgService) {

    fun getGenres(page: Int, pageSize: Int) = client.getGenres(page, pageSize)
}