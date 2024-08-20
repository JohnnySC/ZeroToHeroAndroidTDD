package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task033UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_change_text_recreated(): Unit = with(composeTestRule) {
        onNodeWithText("That's right!").assertDoesNotExist()
        onNodeWithText("Click me!").assertHasClickAction()

        onNodeWithText("Click me!").performClick()
        onNodeWithText("Click me!").assertDoesNotExist()
        onNodeWithText("That's right!").assertExists().assertHasClickAction()

        activityRule.scenario.recreate()
        onNodeWithText("Click me!").assertDoesNotExist()
        onNodeWithText("That's right!").assertExists().assertHasClickAction()
    }
}