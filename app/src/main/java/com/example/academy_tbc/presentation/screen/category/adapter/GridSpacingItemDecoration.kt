package com.example.academy_tbc.presentation.screen.category.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class InnerGridSpacingItemDecoration(
    private val spanCount: Int,
    private val horizontalSpacing: Int,
    private val verticalSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (column == 0) {
            outRect.right = horizontalSpacing / 2
        } else {
            outRect.left = horizontalSpacing / 2
        }

        if (position >= spanCount) {
            outRect.top = verticalSpacing
        }
    }
}