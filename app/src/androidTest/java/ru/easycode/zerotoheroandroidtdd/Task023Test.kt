package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
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
class Task023Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_recycler() {
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

        onView(
            allOf(
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                isAssignableFrom(RecyclerView::class.java),
                withId(R.id.recyclerView)
            )
        ).check(isCompletelyBelow(withId(R.id.inputEditText)))

        onView(withId(R.id.inputEditText)).perform(typeText("first text"))
        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.inputEditText)).check(matches(withText("")))

        onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(0, R.id.elementTextView))
            .check(matches(withText("first text")))

        onView(withId(R.id.inputEditText)).perform(typeText("second text"))
        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.inputEditText)).check(matches(withText("")))

        onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(0, R.id.elementTextView))
            .check(matches(withText("first text")))

        onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(1, R.id.elementTextView))
            .check(matches(withText("second text")))


        for (i in 0..10) {
            onView(withId(R.id.inputEditText)).perform(typeText("text number $i"))
            onView(withId(R.id.actionButton)).perform(click())
            onView(withId(R.id.inputEditText)).check(matches(withText("")))

            onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(i + 2, R.id.elementTextView))
                .check(matches(withText("text number $i")))
        }

        activityScenarioRule.scenario.recreate()

        onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(0, R.id.elementTextView))
            .check(matches(withText("first text")))

        onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(1, R.id.elementTextView))
            .check(matches(withText("second text")))

        for (i in 0..10) {
            onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(i + 2, R.id.elementTextView))
                .check(matches(withText("text number $i")))
        }
    }
}