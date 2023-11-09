package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task021Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_watch_input() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.inputEditText)
            )
        ).check(matches(withText("")))

        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
                isAssignableFrom(Button::class.java),
                withId(R.id.actionButton),
                withText("change")
            )
        ).check(matches(isNotEnabled()))

        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
                isAssignableFrom(TextView::class.java),
                withId(R.id.titleTextView),
                withText("Hello World!")
            )
        ).check(matches(isDisplayed()))

        onView(withId(R.id.inputEditText)).perform(typeText("m"))
        onView(withId(R.id.inputEditText)).check(matches(withText("m")))
        onView(withId(R.id.actionButton)).check(matches(isNotEnabled()))

        onView(withId(R.id.inputEditText)).perform(typeText("i"))
        onView(withId(R.id.inputEditText)).check(matches(withText("mi")))
        onView(withId(R.id.actionButton)).check(matches(isNotEnabled()))

        onView(withId(R.id.inputEditText)).perform(typeText("n"))
        onView(withId(R.id.inputEditText)).check(matches(withText("min")))
        onView(withId(R.id.actionButton)).check(matches(isEnabled()))

        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.inputEditText)).check(matches(withText("")))
        onView(withId(R.id.titleTextView)).check(matches(withText("min")))
        onView(withId(R.id.actionButton)).check(matches(isNotEnabled()))

        activityScenarioRule.scenario.recreate()
        onView(withId(R.id.inputEditText)).check(matches(withText("")))
        onView(withId(R.id.titleTextView)).check(matches(withText("min")))
        onView(withId(R.id.actionButton)).check(matches(isNotEnabled()))
    }
}