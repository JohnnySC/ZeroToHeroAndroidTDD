package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers.allOf

class AddPage : AbstractPage(R.id.addLayout) {

    fun clickSaveButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(rootClass())),
                withParent(withId(root)),
                isAssignableFrom(Button::class.java),
                withId(R.id.saveButton),
                withText("save")
            )
        ).perform(click())
    }

    fun inputText(text: String) {
        onView(
            allOf(
                withParent(isAssignableFrom(rootClass())),
                withParent(withId(root)),
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.addInputEditText)
            )
        ).perform(typeText(text), closeSoftKeyboard())
    }
}