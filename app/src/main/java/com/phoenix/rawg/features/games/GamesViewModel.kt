package com.phoenix.rawg.features.games

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phoenix.rawg.shared.BaseViewModel
import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.network.ResultWrapper
import com.phoenix.rawg.shared.store.models.response.GameResponse
import com.phoenix.rawg.shared.store.repositories.GamesRepository
import com.phoenix.rawg.shared.store.repositories.GenresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamesViewModel @Inject constructor(private val gamesRepository: GamesRepository,
                                         private val genresRepository: GenresRepository,
                                         @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseViewModel() {

    val originalGames = MutableLiveData<List<GameResponse>>(emptyList())
    val filteredGames = MutableLiveData<List<GameResponse>>(emptyList())
    val favoriteGenres = MutableLiveData<List<String>>()
    val isSearchApplied = MutableLiveData(false)
    var page = Pair(1, 20)
    val count = MutableLiveData(0)

    init {
        originalGames.observeForever {
            page = Pair((it.orEmpty().size / 20).toInt() + 1, 20)
            filteredGames.postValue(it.orEmpty())
        }
        loadGames()
    }

    fun loadGames() {
        isLoading.postValue(true)
        viewModelScope.launch (dispatcher) {
            isLoading.postValue(true)
            try {
                val genres = genresRepository.getFavoriteGenresQueryServerIds()
                val response = gamesRepository.getGames(
                    page = page.first,
                    pageSize = page.second,
                    genreIds = genres.joinToString { it })

                when(response) {
                    is ResultWrapper.GenericError -> TODO()
                    ResultWrapper.NetworkError -> TODO()
                    is ResultWrapper.Success -> {
                        originalGames.postValue(originalGames.value.orEmpty().plus(response.data.results))
                        count.postValue(response.data.count)
                    }
                }
            } catch (e: Exception) {
                handleError(e) {
                    loadGames()
                }
            }
        }.invokeOnCompletion {
            isLoading.postValue(false)
        }
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

}