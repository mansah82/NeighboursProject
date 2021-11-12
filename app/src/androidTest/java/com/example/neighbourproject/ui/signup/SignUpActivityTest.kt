package com.example.neighbourproject.ui.signup

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

@RunWith(AndroidJUnit4::class)
class SignUpActivityTest {

    private lateinit var userName: String
    private lateinit var password: String

    @get:Rule
    var activityRule: ActivityScenarioRule<SignUpActivity>
            = ActivityScenarioRule(SignUpActivity::class.java)

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
        onView(withId(R.id.editTextEmailAddress))
            .perform(typeText(userName), closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(typeText("pwd"), closeSoftKeyboard())

        onView(withId(R.id.signUpButton)).perform(click())

        onView(withText("SIGN UP")).check(matches(isDisplayed()))
    }

    @Test
    fun test_invalid_username() {
        onView(withId(R.id.editTextEmailAddress))
            .perform(typeText("userName"), closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.signUpButton)).perform(click())

        onView(withText("SIGN UP")).check(matches(isDisplayed()))
    }

    @Test
    fun test_to_login() {
        onView(withId(R.id.editTextEmailAddress))
            .perform(typeText(userName), closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.signUpButton)).perform(click())

        onView(withText("LOG IN")).check(matches(isDisplayed()))
    }
}