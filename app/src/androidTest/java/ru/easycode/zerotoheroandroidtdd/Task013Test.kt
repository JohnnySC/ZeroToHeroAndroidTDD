package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf
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
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Please also check out unit test
 * @see ru.easycode.zerotoheroandroidtdd.CountTest
 */
@RunWith(AndroidJUnit4::class)
class Task013Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_increment() {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.countTextView),
                withText("0"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.incrementButton),
                withText("+"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(isCompletelyRightOf(withId(R.id.countTextView)))

        onView(
            allOf(
                withId(R.id.decrementButton),
                withText("-"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(isCompletelyLeftOf(withId(R.id.countTextView)))

        onView(withId(R.id.decrementButton)).check(matches(isNotEnabled()))
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.countTextView)).check(matches(withText("2")))
        onView(withId(R.id.decrementButton)).check(matches(isEnabled()))

        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.countTextView)).check(matches(withText("4")))
        onView(withId(R.id.incrementButton)).check(matches(isNotEnabled()))
        onView(withId(R.id.decrementButton)).check(matches(isEnabled()))

        activityScenarioRule.scenario.recreate()
        onView(withId(R.id.countTextView)).check(matches(withText("4")))
        onView(withId(R.id.incrementButton)).check(matches(isNotEnabled()))
        onView(withId(R.id.decrementButton)).check(matches(isEnabled()))

        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.countTextView)).check(matches(withText("2")))
        onView(withId(R.id.incrementButton)).check(matches(isEnabled()))
        onView(withId(R.id.decrementButton)).check(matches(isEnabled()))

        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.countTextView)).check(matches(withText("0")))
        onView(withId(R.id.incrementButton)).check(matches(isEnabled()))
        onView(withId(R.id.decrementButton)).check(matches(isNotEnabled()))

        activityScenarioRule.scenario.recreate()
        onView(withId(R.id.countTextView)).check(matches(withText("0")))
        onView(withId(R.id.incrementButton)).check(matches(isEnabled()))
        onView(withId(R.id.decrementButton)).check(matches(isNotEnabled()))
    }
}