package com.thirdwayv.rawg.features.favoriteGenres

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.thirdwayv.rawg.shared.store.models.response.GenreResponse
import com.thirdwayv.rawg.BR

data class GenreItemViewModel(val genreResponse: GenreResponse, val isFavorite: Boolean): BaseObservable() {

    @get:Bindable
    var favorite: Boolean = isFavorite
    set(value) {
        field = value
        notifyPropertyChanged(BR.favorite)
    }
}