package com.example.academy_tbc.presentation.screen.home.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.presentation.extension.dpToPx

class HorizontalMarginItemDecoration(
    private val startMarginDp: Int,
    private val endMarginDp: Int,
    private val itemSpacingDp: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val context = view.context
        val startMarginPx = startMarginDp.dpToPx(context)
        val endMarginPx = endMarginDp.dpToPx(context)
        val spacingPx = itemSpacingDp.dpToPx(context)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        when (position) {
            0 -> {
                outRect.left = startMarginPx
                outRect.right = spacingPx
            }
            itemCount - 1 -> {
                outRect.left = 0
                outRect.right = endMarginPx
            }
            else -> {
                outRect.left = 0
                outRect.right = spacingPx
            }
        }
    }
}