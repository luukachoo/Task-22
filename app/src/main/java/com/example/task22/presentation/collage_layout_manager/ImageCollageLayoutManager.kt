package com.example.task22.presentation.collage_layout_manager

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class PhotoCollageLayoutManager : RecyclerView.LayoutManager() {
    private var isLayoutCompleted = false

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (isLayoutCompleted) {
            return
        }

        detachAndScrapAttachedViews(recycler)
        if (itemCount == 0) {
            return
        }

        val totalWidth = width - paddingLeft - paddingRight
        val totalHeight = height - paddingTop - paddingBottom
        var top = paddingTop
        var left = paddingLeft
        for (i in 0 until min(itemCount, 3)) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val widthSpec = View.MeasureSpec.makeMeasureSpec(totalWidth, View.MeasureSpec.EXACTLY)
            val heightSpec = View.MeasureSpec.makeMeasureSpec(totalHeight, View.MeasureSpec.EXACTLY)
            layoutDecorated(view, left, top, left + totalWidth, top + totalHeight)

            if (i == 0) {
                top += totalHeight
            } else {
                left += totalWidth
            }
        }

        isLayoutCompleted = true
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        Log.d("PhotoCollageLayoutManager", "onAttachedToWindow")
    }

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        super.onDetachedFromWindow(view, recycler)
        Log.d("PhotoCollageLayoutManager", "onDetachedFromWindow")
    }

}
