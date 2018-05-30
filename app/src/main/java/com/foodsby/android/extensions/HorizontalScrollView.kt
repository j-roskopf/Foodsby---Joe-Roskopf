package com.foodsby.android.extensions

import android.graphics.Rect
import android.view.View
import android.widget.HorizontalScrollView

fun HorizontalScrollView.isViewVisible(view: View): Boolean {
    val scrollBounds = Rect()
    getDrawingRect(scrollBounds)

    val left = view.x
    val right = left + view.width

    return scrollBounds.left <= left && scrollBounds.right >= right
}

fun HorizontalScrollView.directionFromViewOffset(view: View): Int {
    val scrollBounds = Rect()
    getDrawingRect(scrollBounds)
    return if(view.x <= scrollBounds.left) {
        View.FOCUS_LEFT
    } else {
        View.FOCUS_RIGHT
    }
}