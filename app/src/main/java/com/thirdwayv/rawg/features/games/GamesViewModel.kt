package com.thirdwayv.rawg.features.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirdwayv.rawg.shared.BaseViewModel
import javax.inject.Inject

class GamesViewModel @Inject constructor(): BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}