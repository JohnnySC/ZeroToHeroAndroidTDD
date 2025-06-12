package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task037UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun correct() = with(composeTestRule) {
        val question = onNodeWithText("some question")
        val correctChoice = onNodeWithText("correct")
        val inCorrectChoice = onNodeWithText("incorrect")

        question.assertHasNoClickAction().assertExists()
        correctChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Yellow)
        inCorrectChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Yellow)

        correctChoice.performClick()

        question.assertHasNoClickAction().assertExists()
        correctChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Green)
        inCorrectChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Gray)
    }

    @Test
    fun incorrect() = with(composeTestRule) {
        val question = onNodeWithText("some question")
        val correctChoice = onNodeWithText("correct")
        val inCorrectChoice = onNodeWithText("incorrect")

        question.assertHasNoClickAction().assertExists()
        correctChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Yellow)
        inCorrectChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Yellow)

        inCorrectChoice.performClick()

        question.assertHasNoClickAction().assertExists()
        correctChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Green)
        inCorrectChoice
            .assertHasClickAction()
            .assertBackgroundColor(Color.Red)
    }
}

fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
    val capturedName = captureToImage().colorSpace.name
    assertEquals(expectedBackground.colorSpace.name, capturedName)
}