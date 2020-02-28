package com.practice.jsonplaceholderapp.ui_tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.practice.jsonplaceholderapp.R
import com.practice.jsonplaceholderapp.ui.JSONActivity
import com.practice.jsonplaceholderapp.ui.JSONAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class JSONListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(JSONActivity::class.java)

    @Test
    fun listFragmentVisibleOnLaunch() {
        onView(withId(R.id.jsonRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testJsonItemClick() {
        onView(withId(R.id.jsonRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.jsonRecyclerView)).perform(actionOnItemAtPosition<JSONAdapter.ViewHolder>(1, click()))
        Thread.sleep(3000)

        onView(withId(R.id.jsonUserIdEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.jsonIdTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.jsonTitleEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.jsonBodyEditText)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddJsonClickAndPost() {
        onView(withId(R.id.jsonRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.addNewJsonButton)).perform(click())
        Thread.sleep(3000)

        onView(withId(R.id.jsonTitleEditText)).perform(click())
        onView(withId(R.id.jsonTitleEditText)).perform(typeText("foo"))
        onView(withId(R.id.jsonBodyEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.jsonBodyEditText)).perform(typeText("bar"))
        onView(withId(R.id.jsonUserIdEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.jsonUserIdEditText)).perform(typeText("1"))
        onView(withId(R.id.addJsonButton)).perform(click())
        Thread.sleep(3000)

        onView(withId(R.id.jsonRecyclerView)).check(matches(isDisplayed()))
    }


}