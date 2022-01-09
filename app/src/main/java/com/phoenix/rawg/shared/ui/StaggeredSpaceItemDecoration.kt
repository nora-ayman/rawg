package com.phoenix.rawg.shared.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.ThreadLocalRandom

class StaggeredSpaceItemDecoration: RecyclerView.ItemDecoration() {

    private val verticalSpace: Int = 16

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = verticalSpace
        outRect.left = verticalSpace
        outRect.top = verticalSpace
        outRect.bottom = ThreadLocalRandom.current().nextInt(verticalSpace * 2, verticalSpace * 4)

    }
}