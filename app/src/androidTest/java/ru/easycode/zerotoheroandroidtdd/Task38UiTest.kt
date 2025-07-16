package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task38UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun emailValidation(): Unit = with(composeTestRule) {
        val input = onNodeWithTag("emailInputTag")
        val button = onNodeWithTag("loginButtonTag")

        button.assertIsNotEnabled()
        input.assert(hasText(""))

        input.performTextInput("example")
        input.assert(hasText("example"))
        button.assertIsNotEnabled()

        input.performTextInput("@")
        input.assert(hasText("example@"))
        button.assertIsNotEnabled()

        input.performTextInput("gmail.")
        input.assert(hasText("example@gmail."))
        button.assertIsNotEnabled()

        input.performTextInput("com")
        input.assert(hasText("example@gmail.com"))
        button.assertIsEnabled()
    }
}