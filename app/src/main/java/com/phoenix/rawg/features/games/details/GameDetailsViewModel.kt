package com.phoenix.rawg.features.games.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phoenix.rawg.shared.BaseViewModel
import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.store.models.response.GameDetailsResponse
import com.phoenix.rawg.shared.store.models.response.GameScreenshotResponse
import com.phoenix.rawg.shared.store.models.response.GameTrailerResponse
import com.phoenix.rawg.shared.store.repositories.GamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
    private val gamesRepository: GamesRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseViewModel() {

    val gameDetails = MutableLiveData<GameDetailsResponse?>()
    val trailer = MutableLiveData<GameTrailerResponse.Base?>()
    val screenshots = MutableLiveData<List<GameScreenshotResponse>>()
    val isVideoPlaying = MutableLiveData(false)

    var gameId: Int? = null
    set(value) {
        field = value
        if (value != null)
            loadData()
    }

    private fun loadData() {

        viewModelScope.launch {
            isLoading.postValue(true)
                combine(gamesRepository
                    .getGameDetails(gameId!!),
                    gamesRepository.getGameTrailers(gameId!!),
                    gamesRepository.getGameScreenshots(gameId!!)) { details, trailers, screenshots ->
                    return@combine Triple(details, trailers, screenshots)
                }.catch {
                        handleError(it) {
                            loadData()
                        }
                    }
                    .onCompletion {
                        isLoading.postValue(false)
                    }
                .flowOn(dispatcher)
                    .collect {
                        gameDetails.postValue(it.first)
                        trailer.postValue(it.second?.results?.firstOrNull())
                        screenshots.postValue(it.third.results)
                    }

        }
    }
    fun setTrailerVideoStatus(isPlaying: Boolean) {
        isVideoPlaying.postValue(isPlaying)
    }

}