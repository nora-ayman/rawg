package com.phoenix.rawg.shared.utils

import androidx.recyclerview.widget.DiffUtil
import com.phoenix.rawg.features.favoriteGenres.GenreItemViewModel

class ListDiffUtilCallback : DiffUtil.Callback {

    var oldList: List<GenreItemViewModel> = emptyList()
    var newList: List<GenreItemViewModel> = emptyList()

    constructor(oldList: List<GenreItemViewModel>, newList: List<GenreItemViewModel>) {
        this.oldList = oldList
        this.newList = newList
    }
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].genreResponse.id == newList[newItemPosition].genreResponse.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}