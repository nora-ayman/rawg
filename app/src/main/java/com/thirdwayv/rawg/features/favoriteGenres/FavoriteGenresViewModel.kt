package com.thirdwayv.rawg.features.favoriteGenres

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.thirdwayv.rawg.shared.BaseViewModel
import com.thirdwayv.rawg.shared.store.models.objectbox.GenreModel
import com.thirdwayv.rawg.shared.store.models.response.GenreResponse
import com.thirdwayv.rawg.shared.store.repositories.GenresRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers


class FavoriteGenresViewModel @Inject constructor(private val genresRepository: GenresRepository): BaseViewModel() {

    val genres = MutableLiveData<List<GenreItemViewModel>>()
    val favoriteGenres = MutableLiveData<List<GenreModel>>(emptyList())
    var page = Pair(1, 20)
    val count = MutableLiveData(0)
    val compositeDisposable = CompositeDisposable()

    init {
        loadGenres()
    }

    fun loadGenres() {
        isLoading.postValue(true)
        compositeDisposable.add(
            Observable.combineLatest(
                genresRepository
                    .getFavoriteGenres()
                    .subscribeOn(Schedulers.io()),
                genresRepository
                    .getGenres(page = page.first, pageSize = page.second)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
            ) { favorites, allGenres -> Pair(favorites, allGenres) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doFinally {
                    isLoading.postValue(false)
                }
                .subscribe({
                    count.postValue(it.second.count)
                    it.first?.let { favoriteGenres.postValue(it) }
                    mergeGenres(it.first, it.second.results)
                }, {
                    mergeGenres(genresRepository.getLocalFavoriteGenres(), emptyList())
                })
        )
    }

    private fun mergeGenres(favorites: List<GenreModel>, allGenres: List<GenreResponse>) {
        genres.value = genres.value.orEmpty().plus(allGenres.map {
            GenreItemViewModel(
                genreResponse = it,
                isFavorite = favorites.any { favorite -> favorite.serverId == it.id }
            )
        })
        if (genres.value.orEmpty().isEmpty())
            genres.value = favorites.map {
                GenreItemViewModel(
                    genreResponse = GenreResponse(
                        id = it.serverId,
                        name = it.name,
                        thumbnail = it.thumbnail
                    ),
                    isFavorite = true
            )}
        page = Pair(genres.value.orEmpty().size + 1, 20)
    }

    fun updateFavorites(item: GenreItemViewModel) {
        val matchingResult = favoriteGenres.value.orEmpty().firstOrNull { favorite ->
            favorite.serverId == item.genreResponse.id
        }
        if (matchingResult == null)
            genresRepository.addToFavorites(item.genreResponse)
        else
            genresRepository.removeFromFavorites(matchingResult)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}