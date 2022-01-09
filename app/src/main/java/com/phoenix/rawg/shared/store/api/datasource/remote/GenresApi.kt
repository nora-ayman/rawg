package com.phoenix.rawg.shared.store.api.datasource.remote

import com.phoenix.rawg.shared.store.api.IRawgService
import javax.inject.Inject

class GenresApi @Inject constructor(private val client: IRawgService) {

    suspend fun getGenres(page: Int, pageSize: Int) = client.getGenres(page, pageSize)


}