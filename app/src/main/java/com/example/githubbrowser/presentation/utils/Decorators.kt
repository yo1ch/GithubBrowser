package com.example.githubbrowser.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalDecorator(
    private val topOffsetDP : Int,
    private val betweenOffsetDP : Int,
    private val bottomOffsetDP: Int,
    private val startOffsetDP : Int = 0,
    private val endOffsetDP: Int = 0
) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val items = parent.adapter!!.itemCount
        if (position == 0){
            outRect.top += topOffsetDP.dp2px(parent.context)
            outRect.bottom += (betweenOffsetDP/2).dp2px(parent.context)
        }else if( position == items -1){
            outRect.top += (betweenOffsetDP/2).dp2px(parent.context)
            outRect.bottom += bottomOffsetDP.dp2px(parent.context)
        }else{
            outRect.top += (betweenOffsetDP/2).dp2px(parent.context)
            outRect.bottom += (betweenOffsetDP/2).dp2px(parent.context)
        }
        if (startOffsetDP != 0) outRect.left += startOffsetDP.dp2px(parent.context)
        if (endOffsetDP != 0) outRect.right += endOffsetDP.dp2px(parent.context)
    }
}