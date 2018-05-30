package com.foodsby.android.extensions

import android.graphics.Rect
import android.view.View
import android.widget.HorizontalScrollView

fun HorizontalScrollView.isViewVisible(view: View, rect: Rect): Boolean {
    getDrawingRect(rect)

    val left = view.x
    val right = left + view.width

    return rect.left <= left && rect.right >= right
}

fun HorizontalScrollView.directionFromViewOffset(view: View, rect: Rect): Int {
    getDrawingRect(rect)
    return if(view.x <= rect.left) {
        View.FOCUS_LEFT
    } else {
        View.FOCUS_RIGHT
    }
}