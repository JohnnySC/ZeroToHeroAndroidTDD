package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Please also check out the unit test
 * @see ru.easycode.zerotoheroandroidtdd.MainViewModelTest
 */
@RunWith(AndroidJUnit4::class)
class Task015Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_progress_mvvm() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
                isAssignableFrom(ProgressBar::class.java),
                withId(R.id.progressBar)
            )
        ).check(matches(not(isDisplayed())))

        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
                isAssignableFrom(TextView::class.java),
                withId(R.id.titleTextView)
            )
        ).check(matches(not(isDisplayed())))

        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
                isAssignableFrom(Button::class.java),
                withId(R.id.actionButton),
                withText("load")
            )
        ).perform(click())

        onView(withId(R.id.actionButton)).check(matches(isNotEnabled()))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        onView(isRoot()).perform(waitTillDisplayed(R.id.titleTextView, 3500))

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.actionButton)).check(matches(isEnabled()))
    }
}