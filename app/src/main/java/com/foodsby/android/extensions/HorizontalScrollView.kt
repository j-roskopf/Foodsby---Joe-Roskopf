package com.foodsby.android.extensions

import android.graphics.Rect
import android.view.View
import android.widget.HorizontalScrollView

/**
 * Given a view and the rectangle to store it in, return whether or not the view is 100% visible inside the HSV
 *
 * @param view - the view to check
 * @param rect - the rectangle to store the HSV bounds. This is a parameter for easter testing purposes
 *
 * @return - whether or not the passed in view is 100% visible
 */
fun HorizontalScrollView.isViewVisible(view: View, rect: Rect): Boolean {
    getDrawingRect(rect)

    val left = view.x
    val right = left + view.width

    return rect.left <= left && rect.right >= right
}

/**
 * If a view is not 100% contained inside our HSV, we need to know if the HSV needs to scroll left or right to show it
 *
 * @param view - the view to check
 * @param rect - the rect to store the HSV bounds in
 *
 * @return Int representing View.FOCUS_LEFT if the HSV should scroll left or View.FOCUS_RIGHT if the HSV should scroll right
 */
fun HorizontalScrollView.directionFromViewOffset(view: View, rect: Rect): Int {
    getDrawingRect(rect)
    return if(view.x <= rect.left) {
        View.FOCUS_LEFT
    } else {
        View.FOCUS_RIGHT
    }
}