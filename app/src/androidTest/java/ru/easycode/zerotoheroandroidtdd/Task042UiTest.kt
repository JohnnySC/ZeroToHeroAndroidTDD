package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Also check ListViewModelTest
 */
@RunWith(AndroidJUnit4::class)
class Task042UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test(): Unit = with(composeTestRule) {
        val textField = onNodeWithTag("textField")
        val addButton = onNodeWithTag("addButton")

        textField.performTextReplacement("text number one")
        addButton.performClick()
        textField.assertTextEquals("")
        assertTextAtPosition(0, "text number one")

        textField.performTextReplacement("text number two")
        addButton.performClick()
        textField.assertTextEquals("")
        assertTextAtPosition(0, "text number two")
        assertTextAtPosition(1, "text number one")

        activityRule.scenario.recreate()

        assertTextAtPosition(0, "text number two")
        assertTextAtPosition(1, "text number one")
    }

    private fun assertTextAtPosition(position: Int, text: String) = with(composeTestRule) {
        onNodeWithTag("ListLazyColumn")
            .performScrollToNode(hasTestTag("Element at $position"))
            .assertIsDisplayed()

        onNodeWithTag("Element at $position", useUnmergedTree = true)
            .assertTextEquals(text)
    }
}