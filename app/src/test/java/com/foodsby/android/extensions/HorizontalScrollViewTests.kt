package com.foodsby.android.extensions

import android.graphics.Rect
import android.view.View
import android.widget.HorizontalScrollView
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HorizontalScrollViewTests {

    @Test
    fun isViewVisibleTest() {
        /**
         * Given a view in our horizontal scroll view, we want to make sure that it's entirely visible
         */

        val horizontalScrollViewLeft = 0
        val horizontalScrollViewRight = 100
        val horizontalScrollView = mock(HorizontalScrollView::class.java)

        //Setup visible view
        val visibleViewToTest = mock(View::class.java)

        Mockito.`when`(visibleViewToTest.left).thenReturn(3)
        Mockito.`when`(visibleViewToTest.right).thenReturn(9)

        val visibleViewWidth = visibleViewToTest.right  - visibleViewToTest.left
        Mockito.`when`(visibleViewToTest.width).thenReturn(visibleViewWidth)

        val visibleMockedRect = mock(Rect::class.java)
        visibleMockedRect.left = horizontalScrollViewLeft
        visibleMockedRect.right = horizontalScrollViewRight

        //the total width of our HSV right now is 0-100, while the view to test is from 3-9, so this view is fully visible
        assertEquals(horizontalScrollView.isViewVisible(visibleViewToTest, visibleMockedRect), true)

        //Setup visible view
        val invisibleViewToTest = mock(View::class.java)

        Mockito.`when`(invisibleViewToTest.x).thenReturn(3.0f)
        Mockito.`when`(invisibleViewToTest.right).thenReturn(102)

        val invisibleViewWidth = invisibleViewToTest.right  - invisibleViewToTest.left
        Mockito.`when`(invisibleViewToTest.width).thenReturn(invisibleViewWidth)

        //the total width of our HSV right now is 0-100, while the view to test is from 3-102, so this view is fully visible
        assertEquals(horizontalScrollView.isViewVisible(invisibleViewToTest, visibleMockedRect), false)
    }

    @Test
    fun directionFromViewOffsetTest() {
        /**
         * If a view is not visible, we want to make sure we tell the HSV to scroll the correct direction
         */
        val horizontalScrollView = mock(HorizontalScrollView::class.java)

        val leftViewToTest = mock(View::class.java)

        Mockito.`when`(leftViewToTest.x).thenReturn(5.0f)

        val leftMockedRect = mock(Rect::class.java)
        leftMockedRect.left = 10

        assertEquals(horizontalScrollView.directionFromViewOffset(leftViewToTest, leftMockedRect), View.FOCUS_LEFT)

        val rightViewToTest = mock(View::class.java)

        Mockito.`when`(rightViewToTest.x).thenReturn(50.0f)

        val rightMockedRect = mock(Rect::class.java)
        rightMockedRect.left = 10

        assertEquals(horizontalScrollView.directionFromViewOffset(rightViewToTest, rightMockedRect), View.FOCUS_RIGHT)
    }
}