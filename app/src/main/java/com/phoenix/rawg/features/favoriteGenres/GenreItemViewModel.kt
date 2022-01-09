package com.phoenix.rawg.features.favoriteGenres

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.phoenix.rawg.shared.store.models.response.GenreResponse
import com.phoenix.rawg.BR

data class GenreItemViewModel(val genreResponse: GenreResponse, val isFavorite: Boolean): BaseObservable() {

    @get:Bindable
    var favorite: Boolean = isFavorite
    set(value) {
        field = value
        notifyPropertyChanged(BR.favorite)
    }
}