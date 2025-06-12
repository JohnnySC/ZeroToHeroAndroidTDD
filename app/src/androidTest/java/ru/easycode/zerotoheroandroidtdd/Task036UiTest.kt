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

@RunWith(AndroidJUnit4::class)
class Task036UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test() = with(composeTestRule) {
        val title = onNodeWithText("Title always showing")
        val subTitle = onNodeWithText("disappear after click")
        val removeButton = onNodeWithText("remove ui")

        title.assertHasNoClickAction().assertExists()
        subTitle.assertHasNoClickAction().assertExists()
        removeButton.assertHasClickAction().assertExists()

        removeButton.performClick()
        title.assertHasNoClickAction().assertExists()
        subTitle.assertDoesNotExist()
        removeButton.assertDoesNotExist()

        activityRule.scenario.recreate()
        title.assertHasNoClickAction().assertExists()
        subTitle.assertDoesNotExist()
        removeButton.assertDoesNotExist()
    }
}