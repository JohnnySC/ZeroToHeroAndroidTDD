package ru.easycode.zerotoheroandroidtdd

import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf

abstract class AbstractPage(protected val root: Int) {

    fun checkVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(FrameLayout::class.java),
                withId(root)
            )
        ).check(matches(isDisplayed()))
    }

    fun checkNotVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(FrameLayout::class.java),
                withId(root)
            )
        ).check(doesNotExist())
    }
}