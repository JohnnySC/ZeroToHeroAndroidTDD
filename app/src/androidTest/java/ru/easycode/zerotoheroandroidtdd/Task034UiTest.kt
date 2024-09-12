package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
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
class Task034UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun increment(): Unit = with(composeTestRule) {
        onNodeWithText("0").assertHasNoClickAction()
        onNodeWithText("increment").assertHasClickAction()

        onNodeWithText("increment").performClick()
        onNodeWithText("0").assertDoesNotExist()
        onNodeWithText("1").assertHasNoClickAction()

        onNodeWithText("increment").performClick()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithText("2").assertHasNoClickAction()

        activityRule.scenario.recreate()
        onNodeWithText("2").assertHasNoClickAction()
    }
}