package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task039UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun progress(): Unit = with(composeTestRule) {
        val resultText = onNodeWithTag("result")
        val loadButton = onNodeWithTag("loadButton")
        val progress = onNodeWithTag("progress")

        resultText.assertDoesNotExist()
        progress.assertDoesNotExist()

        loadButton.performClick()

        loadButton.assertDoesNotExist()
        resultText.assertDoesNotExist()
        progress.assertIsDisplayed()

        waitUntilDoesNotExist(hasTestTag("progress"), 3000)

        progress.assertDoesNotExist()
        loadButton.assertDoesNotExist()
        resultText.assertTextEquals("Success!").assertIsDisplayed()
    }
}