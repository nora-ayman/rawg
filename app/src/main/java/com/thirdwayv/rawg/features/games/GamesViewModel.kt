package com.thirdwayv.rawg.features.games

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirdwayv.rawg.shared.BaseViewModel
import com.thirdwayv.rawg.shared.store.models.response.GameResponse
import com.thirdwayv.rawg.shared.store.repositories.GamesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamesViewModel @Inject constructor(private val gamesRepository: GamesRepository): BaseViewModel() {

    val games = MutableLiveData<List<GameResponse>>()
    var page = Pair(1, 20)
    val count = MutableLiveData(0)
    val compositeDisposable = CompositeDisposable()

    init {
        loadGames()
    }

    fun loadGames() {
        isLoading.postValue(true)
        compositeDisposable.add(
            gamesRepository
                .getGames(page = page.first, pageSize = page.second)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    count.postValue(it.count)
                    games.postValue(it.results)
                    isLoading.postValue(false)
                }, {
                    Log.e("", "")
                })

        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}