package com.phoenix.rawg.shared.store.api.datasource.remote

import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.api.IRawgService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GenresApi @Inject constructor(private val client: IRawgService, @IoDispatcher val dispatcher: CoroutineDispatcher): BaseApi() {

    suspend fun getGenres(page: Int, pageSize: Int) = safeApiCall(dispatcher) { client.getGenres(page, pageSize) }


}