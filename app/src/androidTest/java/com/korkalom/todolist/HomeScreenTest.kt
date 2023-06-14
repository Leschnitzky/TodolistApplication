package com.korkalom.todolist

import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.korkalom.todolist.ui.screens.home.FILTER_BTN
import com.korkalom.todolist.ui.screens.home.FILTER_MENU
import com.korkalom.todolist.ui.screens.home.LIST_ITEM
import com.korkalom.todolist.ui.screens.home.TODAY_CARD
import com.korkalom.todolist.ui.screens.home.TOMORROW_CARD
import com.korkalom.todolist.ui.screens.home.UPCOMING_CARD
import com.korkalom.todolist.ui.screens.home.WELCOME_CARD
import com.korkalom.todolist.utils.PAGE_HOME
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
class HomeScreenTest {

    var hiltRule = HiltAndroidRule(this)
    val composeTestRule by lazy { createAndroidComposeRule<MainActivity>() }

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(composeTestRule)

    @Before
    fun init() {
        hiltRule.inject()

    }

    @Test
    fun displaysAllCardsOnStart() {
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${WELCOME_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TODAY_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TOMORROW_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${UPCOMING_CARD}"
        ).assertIsDisplayed()
    }

    @Test
    fun displayAllCardsAfterLongClickingFirstCard() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${TODAY_CARD}").performGesture {
            longClick()
        }
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TODAY_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TOMORROW_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${UPCOMING_CARD}"
        ).assertIsDisplayed()
    }

    @Test
    fun displayAllCardsAfterLongClickingSecondCard() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${TOMORROW_CARD}")
            .performGesture {
                longClick()
            }
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TODAY_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TOMORROW_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${UPCOMING_CARD}"
        ).assertIsDisplayed()
    }

    @Test
    fun displayAllCardsAfterLongClickingThirdCard() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${UPCOMING_CARD}")
            .performGesture {
                longClick()
            }
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TODAY_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TOMORROW_CARD}"
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${UPCOMING_CARD}"
        ).assertIsDisplayed()
    }

    @Test
    fun longClickingCardTwiceShouldReturnAllCardsToNormalSize() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${UPCOMING_CARD}")
            .performGesture {
                longClick()
            }
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${UPCOMING_CARD}")
            .performGesture {
                longClick()
            }

        composeTestRule.onNodeWithContentDescription(
            "${PAGE_HOME}_${TODAY_CARD}"
        ).assertHeightIsAtLeast(100.dp)

    }

    @Test
    fun clickingFilterButtonShowsFilterMenu() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${TODAY_CARD}").onChildren()
            .filterToOne(
                hasContentDescription("${PAGE_HOME}_$FILTER_BTN")
            ).performClick()

        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${FILTER_MENU}")
            .assertIsDisplayed()
    }

    @Test
    fun clickingFilterButtonOnFoldedCardShouldNotShowFilterMenu() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${UPCOMING_CARD}")
            .performGesture {
                longClick()
            }
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${TODAY_CARD}").onChildren()
            .filterToOne(
                hasContentDescription("${PAGE_HOME}_$FILTER_BTN")
            ).performClick()

        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${FILTER_MENU}")
            .assertDoesNotExist()

    }



    @Test
    fun longClickingTaskItemsShouldMakeThemSelected() {
        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${TODAY_CARD}").onChildren().filterToOne(
            hasContentDescription("${TODAY_CARD}_${LIST_ITEM}_#0")
        ).performGesture {
            longClick()
        }

        composeTestRule.onNodeWithContentDescription("${PAGE_HOME}_${TODAY_CARD}").onChildren().filterToOne(
            hasContentDescription("${TODAY_CARD}_${LIST_ITEM}_#0")
        ).assertIsSelected()
    }



}