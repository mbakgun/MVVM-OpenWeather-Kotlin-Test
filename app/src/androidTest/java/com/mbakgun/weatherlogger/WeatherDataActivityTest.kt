package com.mbakgun.weatherlogger


import android.os.SystemClock
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.mbakgun.weatherlogger.helper.RecyclerViewItemCountAssertion
import com.mbakgun.weatherlogger.view.WeatherDataActivity
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDataActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(WeatherDataActivity::class.java)

    private val waitMs = 750

    @Test
    fun weatherDataDetailDialogShown() {
        SystemClock.sleep(waitMs.toLong())
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.dialog)).check(matches(isDisplayed()))
    }

    @Test
    fun weatherDataActivityMenuSaveTest() {
        SystemClock.sleep(waitMs.toLong())
        val beforeCountFromRecyclerView = RecyclerViewItemCountAssertion.getCountFromRecyclerView(R.id.recyclerView)
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.action_save)).perform(click())
        SystemClock.sleep(waitMs.toLong())
        val afterCountFromRecyclerView = RecyclerViewItemCountAssertion.getCountFromRecyclerView(R.id.recyclerView)
        assertEquals(afterCountFromRecyclerView - beforeCountFromRecyclerView, 1)
    }

    @Test
    fun weatherDataActivityMenuClearTest() {
        SystemClock.sleep(waitMs.toLong())
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.action_clear_all)).perform(click())
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun weatherDataActivityClickTextViewTest() {
        SystemClock.sleep(waitMs.toLong())
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.action_clear_all)).perform(click())
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(0))
        onView(withId(R.id.textViewAdd)).perform(click())
        SystemClock.sleep(waitMs.toLong())
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(1))
    }
}