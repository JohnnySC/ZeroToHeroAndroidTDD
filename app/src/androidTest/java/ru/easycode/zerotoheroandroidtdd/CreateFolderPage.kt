package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.allOf

class CreateFolderPage {

    private val rootId: Int = R.id.createFolderRootLayout
    private val title = onView(
        allOf(
            withParent(isAssignableFrom(LinearLayout::class.java)),
            withParent(withId(rootId)),
            isAssignableFrom(TextView::class.java),
            withId(R.id.createFolderTextView),
            withText("Input folder name")
        )
    )

    fun checkVisibleNow() {
        title.check(matches(isDisplayed()))
    }

    fun inputFolderName(text: String) {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.createFolderEditText),
            )
        ).perform(typeText(text))
    }

    fun clickSaveButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(Button::class.java),
                withId(R.id.saveFolderButton),
                withText("save")
            )
        ).perform(click())
    }

    fun checkNotVisibleNow() {
        title.check(doesNotExist())
    }
}