package com.example.academy_tbc.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MarginItemDecoration(
    private val lastItemSpace: Int,
    private val defaultSpace: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        if (position == itemCount - 1) {
            outRect.right = lastItemSpace
        } else {
            outRect.right = defaultSpace
        }
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val horizontalSpacing: Int,
    private val verticalSpacing: Int,
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = column * horizontalSpacing / spanCount
        outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount
        if (position >= spanCount) outRect.top = verticalSpacing
    }
}