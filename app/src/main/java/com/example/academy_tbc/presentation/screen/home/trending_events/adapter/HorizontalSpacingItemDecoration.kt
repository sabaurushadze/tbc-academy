package com.example.academy_tbc.presentation.screen.home.trending_events.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpacingItemDecoration(
    private val spacing: Int,
    private val addStartSpacing: Boolean = false,
    private val addEndSpacing: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position != RecyclerView.NO_POSITION) {
            outRect.left = if (position == 0 && addStartSpacing) spacing else 0

            outRect.right = if (position == itemCount - 1) {
                if (addEndSpacing) spacing else 0
            } else {
                spacing
            }
        }
    }
}