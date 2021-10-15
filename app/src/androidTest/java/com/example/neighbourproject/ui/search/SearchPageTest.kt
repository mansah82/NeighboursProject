package com.example.neighbourproject.ui.search

import android.util.Log
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.neighbourproject.BuildConfig
import com.example.neighbourproject.R
import org.junit.Rule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.neighbourproject.ui.neigbour.InterestRecyclerAdapter
import org.junit.After

@RunWith(AndroidJUnit4::class)
class SearchPageTest {

    private val TAG = "SearchPageTest"
    private val listItemInTest = 2  //Cea Ceasson, 36 enby
    private val listItemNameInTest = "Cea Ceasson"


    @get:Rule
    var activityRule: ActivityScenarioRule<SearchActivity>
            = ActivityScenarioRule(SearchActivity::class.java)

    @After
    fun cleanup() {
        Log.d(TAG, "cleanup")
        activityRule.scenario.close()
    }

    @Test
    fun verify_build_type() {
        assert(BuildConfig.BUILD_TYPE == "demo")
        Log.d(TAG, "Running with buildtype: ${BuildConfig.BUILD_TYPE}")
    }

    @Test
    fun test_recycler_view_active_on_launched() {
        Log.d(TAG, activityRule.scenario.state.toString())
        onView(withId(R.id.search_result_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_select_list_item_is_active_on_click() {
        Log.d(TAG, activityRule.scenario.state.toString())
        onView(withId(R.id.search_result_list)).perform(
            actionOnItemAtPosition<InterestRecyclerAdapter.ViewHolder>(
                listItemInTest,
                click()
            )
        )
        onView(withId(R.id.neighbour_name)).check(matches(withText(listItemNameInTest)))
    }

    @Test
    fun test_select_list_item_is_active_on_click_navigat_back() {
        Log.d(TAG, activityRule.scenario.state.toString())
        //logMeIn()
        onView(withId(R.id.search_result_list)).perform(
            actionOnItemAtPosition<InterestRecyclerAdapter.ViewHolder>(
                listItemInTest,
                click()
            )
        )
        onView(withId(R.id.neighbour_name)).check(matches(withText(listItemNameInTest)))

        pressBack()

        onView(withId(R.id.search_result_list)).check(matches(isDisplayed()))
    }
}