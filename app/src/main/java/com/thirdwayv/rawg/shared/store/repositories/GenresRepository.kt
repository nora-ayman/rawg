package com.thirdwayv.rawg.shared.store.repositories

import com.thirdwayv.rawg.shared.store.api.datasource.remote.GenresApi
import com.thirdwayv.rawg.shared.store.dao.GenreDao
import com.thirdwayv.rawg.shared.store.models.objectbox.GenreModel
import com.thirdwayv.rawg.shared.store.models.response.GenreResponse
import io.reactivex.Observable
import javax.inject.Inject

class GenresRepository @Inject constructor(private val genresApi: GenresApi,
                                           private val genresDao: GenreDao) {

    fun getGenres(page: Int, pageSize: Int = 20) = genresApi.getGenres(page, pageSize)

    fun addToFavorites(genreResponse: GenreResponse) {
        genresDao.upsert(
            GenreModel(name = genreResponse.name,
            thumbnail = genreResponse.thumbnail,
            serverId = genreResponse.id))
    }

    fun removeFromFavorites(genreModel: GenreModel) {
        genresDao.remove(genreModel)
    }

    fun getFavoriteGenres(): Observable<List<GenreModel>> {
        return Observable.just(genresDao.getAll())
    }

    fun getFavoriteGenresQueryServerIds(): List<String> {
        return genresDao.getAll().map { it.serverId.toString() }
    }

    fun getLocalFavoriteGenres(): List<GenreModel> {
        return genresDao.getAll()
    }
}
