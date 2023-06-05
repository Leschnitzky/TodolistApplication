package com.korkalom.todolist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScreenTest {

    var hiltRule = HiltAndroidRule(this)
    val composeTestRule by lazy { createAndroidComposeRule<MainActivity>() }
    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(composeTestRule)

    @Before
    fun init(){
        hiltRule.inject()

    }

    @Test
    fun displayClearButton() {
        composeTestRule.onNodeWithText("Clear").assertIsDisplayed()
    }
}