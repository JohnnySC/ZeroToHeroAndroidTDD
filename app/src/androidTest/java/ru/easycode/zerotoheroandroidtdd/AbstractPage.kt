package ru.easycode.zerotoheroandroidtdd

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf

abstract class AbstractPage(
    protected val root: Int,
    protected val rootClass: Class<out ViewGroup> = LinearLayout::class.java
) {

    private val rootInteraction = onView(
        allOf(
            isAssignableFrom(rootClass),
            withId(root)
        )
    )

    open fun checkVisibleNow() {
        rootInteraction.check(matches(isDisplayed()))
    }

    fun checkNotVisibleNow() {
        rootInteraction.check(doesNotExist())
    }
}