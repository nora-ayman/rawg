package com.thirdwayv.rawg.features.games.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.thirdwayv.rawg.shared.BaseViewModel
import com.thirdwayv.rawg.shared.store.models.response.GameDetailsResponse
import com.thirdwayv.rawg.shared.store.models.response.GameScreenshotResponse
import com.thirdwayv.rawg.shared.store.models.response.GameTrailerResponse
import com.thirdwayv.rawg.shared.store.repositories.GamesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(val gamesRepository: GamesRepository): BaseViewModel() {

    val compositeDisposable = CompositeDisposable()
    val gameDetails = MutableLiveData<GameDetailsResponse>()
    val trailer = MutableLiveData<GameTrailerResponse.Base?>()
    val screenshots = MutableLiveData<List<GameScreenshotResponse>>()
    val isVideoPlaying = MutableLiveData(false)

    var gameId: Int? = null
    set(value) {
        field = value
        if (value != null)
            loadDetails()
    }

    private fun loadDetails() {
        isLoading.postValue(true)
        compositeDisposable.add(
            Observable.combineLatest(
                gamesRepository
                    .getGameDetails(gameId!!)
                    .subscribeOn(Schedulers.io())
                    .toObservable(),
                gamesRepository
                    .getGameTrailers(gameId!!)
                    .toObservable()
                    .subscribeOn(Schedulers.io()),
                gamesRepository
                    .getGameScreenshots(gameId!!)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
            ) { details, trailer, screenshots -> Triple(details, trailer, screenshots) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doFinally {
                    isLoading.postValue(false)
                }
                .subscribe({
                    gameDetails.postValue(it.first!!)
                    trailer.postValue(it.second?.results?.firstOrNull())
                    screenshots.postValue(it.third.results)
                }, {
                    Log.e("", "")
                })
        )
    }

    fun setTrailerVideoStatus(isPlaying: Boolean) {
        isVideoPlaying.postValue(isPlaying)
    }
}