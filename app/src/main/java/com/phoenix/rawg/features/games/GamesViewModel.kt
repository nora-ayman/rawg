package com.phoenix.rawg.features.games

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phoenix.rawg.shared.BaseViewModel
import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.models.response.GameResponse
import com.phoenix.rawg.shared.store.repositories.GamesRepository
import com.phoenix.rawg.shared.store.repositories.GenresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
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
            page = Pair(it.orEmpty().size + 1, 20)
            filteredGames.postValue(it.orEmpty())
        }
        loadGames()
    }

    fun loadGames() {
        isLoading.postValue(true)
        viewModelScope.launch {
            isLoading.postValue(true)
            genresRepository
                .getFavoriteGenresQueryServerIds()
                .flowOn(dispatcher)
                .catch {
                    handleError(it) {
                        loadGames()

                    }
                }
                .collect {
                    launch {
                        gamesRepository.getGames(
                            page = page.first,
                            pageSize = page.second,
                            genreIds = it.joinToString { it })
                            .onCompletion {
                                isLoading.postValue(false)
                            }
                            .collect {
                                originalGames.postValue(it.results)
                                count.postValue(it.count)
                            }
                }
            }

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