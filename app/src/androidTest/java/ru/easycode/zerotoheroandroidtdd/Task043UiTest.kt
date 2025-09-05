package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Also check ListViewModelTest and RoomTest
 */
@RunWith(AndroidJUnit4::class)
class Task043UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test(): Unit = with(composeTestRule) {
        val textField = onNodeWithTag("textField")
        val addButton = onNodeWithTag("addButton")
        val times = 100
        repeat(times) { index ->
            textField.performTextReplacement("text number $index")
            addButton.performClick()
            textField.assertTextEquals("")
            assertTextAtPosition(0, "text number $index")
        }

        activityRule.scenario.recreate()

        Espresso.closeSoftKeyboard()

        repeat(times) { index ->
            assertTextAtPosition(times - 1 - index, "text number $index")
        }
    }

    private fun assertTextAtPosition(position: Int, text: String) = with(composeTestRule) {
        onNodeWithTag("ListLazyColumn")
            .performScrollToNode(hasTestTag("Element at $position"))
            .assertIsDisplayed()

        onNodeWithTag("Element at $position", useUnmergedTree = true)
            .assertTextEquals(text)
    }
}