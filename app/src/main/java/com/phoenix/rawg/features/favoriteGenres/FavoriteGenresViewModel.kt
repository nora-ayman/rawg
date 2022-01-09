package com.phoenix.rawg.features.favoriteGenres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phoenix.rawg.shared.BaseViewModel
import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.models.objectbox.GenreModel
import com.phoenix.rawg.shared.store.models.response.GenreResponse
import com.phoenix.rawg.shared.store.repositories.GenresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoriteGenresViewModel @Inject constructor(private val genresRepository: GenresRepository,
                                                  @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseViewModel() {

    val genres = MutableLiveData<List<GenreItemViewModel>>()
    val favoriteGenres = MutableLiveData<List<GenreModel>>(emptyList())
    var page = Pair(1, 20)
    val count = MutableLiveData(0)

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading.postValue(true)

            genresRepository.getFavoriteGenres()
                .catch {
                    handleError(it) {
                        loadData()
                    }
                }
                .zip(
                    genresRepository.getGenres(
                        page = page.first,
                        pageSize = page.second
                    )
                ) { favoriteGenres, genres ->

                    return@zip Pair(favoriteGenres, genres.results)
                }.flowOn(dispatcher)
                .onCompletion {
                    isLoading.postValue(false)
                }
                .collect {
                    favoriteGenres.postValue(it.first.orEmpty())
                    mergeGenres(it.first, it.second)
                }

        }
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
        viewModelScope.launch {
            if (matchingResult == null)
                genresRepository.addToFavorites(item.genreResponse)
            else
                genresRepository.removeFromFavorites(matchingResult)
        }
    }

}