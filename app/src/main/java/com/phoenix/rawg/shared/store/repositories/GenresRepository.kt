package com.phoenix.rawg.shared.store.repositories

import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.api.datasource.remote.GenresApi
import com.phoenix.rawg.shared.store.dao.GenreDao
import com.phoenix.rawg.shared.store.models.objectbox.GenreModel
import com.phoenix.rawg.shared.store.models.response.GenreResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GenresRepository @Inject constructor(private val genresApi: GenresApi,
                                           private val genresDao: GenreDao,
                                           @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getGenres(page: Int, pageSize: Int = 20) = genresApi.getGenres(page, pageSize)

    fun addToFavorites(genreResponse: GenreResponse) {
        CoroutineScope(dispatcher).launch {
            genresDao.upsert(
                GenreModel(
                    name = genreResponse.name,
                    thumbnail = genreResponse.thumbnail,
                    serverId = genreResponse.id
                )
            )
        }

    }

    fun removeFromFavorites(genreModel: GenreModel) {
        CoroutineScope(dispatcher).launch {
            genresDao.remove(genreModel)
        }

    }

    fun getFavoriteGenres() = genresDao.getAll()


    fun getFavoriteGenresQueryServerIds() = genresDao.getAll().map { it.serverId.toString()}

}
