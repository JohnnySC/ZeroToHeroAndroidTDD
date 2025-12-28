package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class OrderSettingsPage(private val composeTestRule: ComposeTestRule) {

    fun assertChosen(option: String) {
        composeTestRule.onNodeWithTag("Order option $option")
            .assertTextEquals(option)
            .assertIsSelected()
    }

    fun choose(option: String) {
        composeTestRule.onNodeWithTag("Order option $option")
            .performClick()
    }
}