package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Please also take a look at the unit test
 * @see [ru.easycode.zerotoheroandroidtdd.CountTest]
 */
@RunWith(AndroidJUnit4::class)
class Task035UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun increment(): Unit = with(composeTestRule) {
        onNodeWithText("1").assertHasNoClickAction()
        onNodeWithText("+").assertHasClickAction().assertIsEnabled()
        onNodeWithText("-").assertHasClickAction().assertIsEnabled()

        onNodeWithText("+").performClick()
        onNodeWithText("+").assertHasClickAction().assertIsNotEnabled()
        onNodeWithText("-").assertHasClickAction().assertIsEnabled()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithText("2").assertHasNoClickAction()

        activityRule.scenario.recreate()
        onNodeWithText("+").assertHasClickAction().assertIsNotEnabled()
        onNodeWithText("-").assertHasClickAction().assertIsEnabled()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithText("2").assertHasNoClickAction()
    }

    @Test
    fun decrement(): Unit = with(composeTestRule) {
        onNodeWithText("1").assertHasNoClickAction()
        onNodeWithText("+").assertHasClickAction().assertIsEnabled()
        onNodeWithText("-").assertHasClickAction().assertIsEnabled()

        onNodeWithText("-").performClick()
        onNodeWithText("-").assertHasClickAction().assertIsNotEnabled()
        onNodeWithText("+").assertHasClickAction().assertIsEnabled()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithText("0").assertHasNoClickAction()

        activityRule.scenario.recreate()
        onNodeWithText("-").assertHasClickAction().assertIsNotEnabled()
        onNodeWithText("+").assertHasClickAction().assertIsEnabled()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithText("0").assertHasNoClickAction()
    }
}