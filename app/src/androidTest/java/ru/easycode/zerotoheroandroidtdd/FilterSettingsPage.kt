package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class FilterSettingsPage(private val composeTestRule: ComposeTestRule) {

    private val saveButton =
        composeTestRule.onNode(
            hasTestTag("save button") and
                    hasText("save") and
                    hasClickAction()
        )

    private val filtersMap = mapOf<String, Any>(
        "os" to "Android",
        "os" to "iOS",
        "RAM" to 4,
        "RAM" to 6,
        "RAM" to 8,
    )

    fun assertNothingChosen() {
        filtersMap.forEach { (key, value) ->
            composeTestRule.onNodeWithTag("filter $key $value")
                .assertIsNotSelected()
        }
    }

    fun choose(filter: Pair<String, Any>) {
        composeTestRule.onNodeWithTag("filter ${filter.first} ${filter.second}")
            .performClick()
    }

    fun assertChosen(filter: Pair<String, Any>) {
        composeTestRule.onNodeWithTag("filter ${filter.first} ${filter.second}")
            .assertIsSelected()
    }

    fun unchoose(filter: Pair<String, Any>) {
        composeTestRule.onNodeWithTag("filter ${filter.first} ${filter.second}")
            .performClick()
    }

    fun save() {
        saveButton.performClick()
    }
}