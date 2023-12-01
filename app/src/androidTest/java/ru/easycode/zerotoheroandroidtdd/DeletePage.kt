package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf

class DeletePage : AbstractPage(R.id.deleteLayout) {

    override fun checkVisibleNow() {
        super.checkVisibleNow()
        onView(
            allOf(
                withParent(isAssignableFrom(rootClass)),
                withParent(withId(root)),
                isAssignableFrom(TextView::class.java),
                withId(R.id.titleTextView)
            )
        ).check(matches(withText("Are you sure you want to delete item?")))
    }

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
                withId(R.id.itemTitleTextView)
            )
        ).check(matches(withText(text)))
    }
}