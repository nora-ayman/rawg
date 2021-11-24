package com.thirdwayv.rawg.features.games

import androidx.lifecycle.MutableLiveData
import com.thirdwayv.rawg.shared.BaseViewModel
import com.thirdwayv.rawg.shared.store.models.response.GameResponse
import com.thirdwayv.rawg.shared.store.repositories.GamesRepository
import com.thirdwayv.rawg.shared.store.repositories.GenresRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamesViewModel @Inject constructor(private val gamesRepository: GamesRepository,
                                         private val genresRepository: GenresRepository): BaseViewModel() {

    val originalGames = MutableLiveData<List<GameResponse>>(emptyList())
    val filteredGames = MutableLiveData<List<GameResponse>>(emptyList())
    val favoriteGenres = MutableLiveData<List<String>>()
    val isSearchApplied = MutableLiveData(false)
    var page = Pair(1, 20)
    val count = MutableLiveData(0)
    val compositeDisposable = CompositeDisposable()

    init {
        originalGames.observeForever {
            page = Pair(it.orEmpty().size + 1, 20)
            filteredGames.postValue(it.orEmpty())
        }
        loadFavoriteGenres()
        loadGames()
    }

    fun loadFavoriteGenres() {
        favoriteGenres.value = genresRepository.getFavoriteGenresQueryServerIds()
    }

    fun loadGames() {
        isLoading.postValue(true)
        compositeDisposable.add(
            gamesRepository
                .getGames(
                    page = page.first,
                    pageSize = page.second,
                    genreIds = favoriteGenres.value.orEmpty().joinToString { it })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    isLoading.postValue(false)
                }
                .subscribe({
                    count.postValue(it.count)
                    originalGames.postValue(originalGames.value.orEmpty().plus(it.results))
                    isLoading.postValue(false)
                }, {
                    handleError(it) {
                        if (it)
                            loadGames()
                    }
                })

        )
    }

    fun searchGenresByName(query: String?) {
        if (query.isNullOrEmpty()) {
            filteredGames.value = originalGames.value
            isSearchApplied.value = false
        } else {
            filteredGames.value = originalGames.value.orEmpty()
                .filter { it.name.lowercase().contains(query.lowercase()) }
            isSearchApplied.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}