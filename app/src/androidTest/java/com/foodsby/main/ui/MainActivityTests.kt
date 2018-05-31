package com.foodsby.main.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.foodsby.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTests() {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
            MainActivity::class.java)

    @Test
    fun verifyOnlyDaysWithDeliveriesExist() {
        //verify our seg control group is displayed
        onView(withId(R.id.mainSegmentedGroup)).check(matches(isDisplayed()))

        //verify it has 5 days in it (which is the amount of days that have deliveries in our sample JSON)
        onView(withId(R.id.mainSegmentedGroup)).check(matches(withChildViewCount(5)))
    }

    /**
     * Makes sure a given view contains the amount of children passed in
     *
     * @param count - count of children of the view
     *
     * @return Matcher<View> verifying the view matches the child count
     */
    private fun withChildViewCount(count: Int): Matcher<View> {
        return object : BoundedMatcher<View, ViewGroup>(ViewGroup::class.java) {
            override fun matchesSafely(viewGroup: ViewGroup): Boolean {
                var matchCount = 0
                for (i in 0 until viewGroup.childCount) {
                    matchCount++
                }

                return matchCount == count
            }

            override fun describeTo(description: Description) {
                description.appendText("ViewGroup with child-count=$count")
            }
        }
    }
}