package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
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
class Task022Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_list() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.topLayout)),
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.inputEditText)
            )
        ).check(matches(withText("")))

        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.topLayout)),
                isAssignableFrom(Button::class.java),
                withId(R.id.actionButton),
                withText("create")
            )
        ).check(isCompletelyRightOf(withId(R.id.inputEditText)))

        onView(withId(R.id.inputEditText)).perform(typeText("first text"))
        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.inputEditText)).check(matches(withText("")))

        onView(
            allOf(
                withText("first text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withId(R.id.inputEditText)))

        onView(withId(R.id.inputEditText)).perform(typeText("second text"))
        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.inputEditText)).check(matches(withText("")))

        onView(
            allOf(
                withText("first text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withId(R.id.inputEditText)))

        onView(
            allOf(
                withText("second text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withText("first text")))

        onView(withId(R.id.inputEditText)).perform(typeText("third text"))
        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.inputEditText)).check(matches(withText("")))

        onView(
            allOf(
                withText("first text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withId(R.id.inputEditText)))

        onView(
            allOf(
                withText("second text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withText("first text")))

        onView(
            allOf(
                withText("third text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withText("second text")))


        activityScenarioRule.scenario.recreate()

        onView(
            allOf(
                withText("first text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withId(R.id.inputEditText)))

        onView(
            allOf(
                withText("second text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withText("first text")))

        onView(
            allOf(
                withText("third text"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.contentLayout)),
                isAssignableFrom(TextView::class.java),
            )
        ).check(isCompletelyBelow(withText("second text")))
    }
}