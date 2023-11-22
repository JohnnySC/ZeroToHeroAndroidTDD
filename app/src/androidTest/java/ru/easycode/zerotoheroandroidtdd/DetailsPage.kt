package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers.allOf

class DetailsPage : AbstractPage(R.id.detailsLayout) {

    private val input = onView(
        allOf(
            withParent(isAssignableFrom(rootClass)),
            withParent(withId(root)),
            isAssignableFrom(TextInputEditText::class.java),
            withId(R.id.itemInputEditText)
        )
    )

    fun clickDeleteButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(rootClass)),
                withParent(withId(root)),
                isAssignableFrom(Button::class.java),
                withId(R.id.deleteButton),
                withText("delete")
            )
        ).perform(click())
    }

    fun checkText(text: String) {
        onView(
            allOf(
                withParent(isAssignableFrom(rootClass)),
                withParent(withId(root)),
                isAssignableFrom(TextView::class.java),
                withId(R.id.itemTextView)
            )
        ).check(matches(withText(text)))

        input.check(matches(withText(text)))
    }

    fun clickUpdateButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(rootClass)),
                withParent(withId(root)),
                isAssignableFrom(Button::class.java),
                withId(R.id.updateButton),
                withText("update")
            )
        ).perform(click())
    }

    fun changeText(text: String) {
        input.perform(clearText(), typeText(text))
    }
}