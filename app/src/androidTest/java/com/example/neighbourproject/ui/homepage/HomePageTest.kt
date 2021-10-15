package com.example.neighbourproject.ui.homepage

import android.util.Log
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.neighbourproject.BuildConfig
import com.example.neighbourproject.R
import org.junit.Rule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

//https://developer.android.com/training/testing/ui-testing/espresso-testing
@RunWith(AndroidJUnit4::class)
class HomePageTest {

    private lateinit var userName: String
    private lateinit var password: String

    @get:Rule
    var activityRule: ActivityScenarioRule<HomePageActivity>
            = ActivityScenarioRule(HomePageActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        userName = "espresso@espresso.com"
        password = "Espresso"
    }

    @Test
    fun verify_build_type(){
        assert(BuildConfig.BUILD_TYPE == "demo")
        Log.d("ApplicationTest", "Running with buildtype: ${BuildConfig.BUILD_TYPE}")
    }

    @Test
    fun test_to_short_password() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText(userName), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("pwd"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withText("SIGN IN")).check(matches(isDisplayed()))
    }

    @Test
    fun test_invalid_username() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText("userName"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withText("SIGN IN")).check(matches(isDisplayed()))
    }

    @Test
    fun test_to_login() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText(userName), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.minAge)).check(matches(isDisplayed()))
        onView(withId(R.id.search_result_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigate_to_register() {
        onView(withId(R.id.registerButton)).perform(click())

        onView(withText("SIGN UP")).check(matches(isDisplayed()))
    }
}