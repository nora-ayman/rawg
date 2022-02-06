package com.phoenix.rawg.features.games.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phoenix.rawg.shared.BaseViewModel
import com.phoenix.rawg.shared.di.IoDispatcher
import com.phoenix.rawg.shared.network.ResultWrapper
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

        viewModelScope.launch (dispatcher) {
            isLoading.postValue(true)

            try {

                val detailsResponse = gamesRepository
                    .getGameDetails(gameId!!)
                val trailersResponse = gamesRepository.getGameTrailers(gameId!!)
                val screenshotsResponse = gamesRepository.getGameScreenshots(gameId!!)

                when(detailsResponse) {
                    is ResultWrapper.GenericError -> {
                        isLoading.postValue(false)

                    }
                    ResultWrapper.NetworkError -> {
                        isLoading.postValue(false)
                    }
                    is ResultWrapper.Success -> {
                        gameDetails.postValue(detailsResponse.data.results.first())
                    }
                }

                when (trailersResponse) {
                    is ResultWrapper.GenericError -> {
                        isLoading.postValue(false)
                    }
                    ResultWrapper.NetworkError -> {
                        isLoading.postValue(false)
                    }
                    is ResultWrapper.Success -> {
                        trailer.postValue(trailersResponse.data.results.firstOrNull())
                    }
                }

                when(screenshotsResponse) {
                    is ResultWrapper.GenericError -> {
                        isLoading.postValue(false)
                    }
                    ResultWrapper.NetworkError -> {
                        isLoading.postValue(false)

                    }
                    is ResultWrapper.Success -> {
                        screenshots.postValue(screenshotsResponse.data.results)
                    }

                }
            } catch (e: Exception) {
                handleError(e) {
                    loadData()
                }
            }

        }.invokeOnCompletion {
            isLoading.postValue(false)

        }
    }
    fun setTrailerVideoStatus(isPlaying: Boolean) {
        isVideoPlaying.postValue(isPlaying)
    }

}