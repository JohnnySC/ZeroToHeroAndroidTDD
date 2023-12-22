package ru.easycode.zerotoheroandroidtdd

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

fun waitTillDisplayed(id: Int, timeout: Long) = waitForView(id, isDisplayed(), timeout)

fun waitForView(id: Int, viewMatcher: Matcher<View>, timeout: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with id $id; during $timeout millis."
        }

        override fun perform(uiController: UiController, rootView: View) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + timeout

            do {
                rootView.findViewById<View?>(id)?.let { view ->
                    if (viewMatcher.matches(view)) return
                }

                uiController.loopMainThreadForAtLeast(100)
            } while (System.currentTimeMillis() < endTime)

            throw PerformException.Builder()
                .withCause(TimeoutException())
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(rootView))
                .build()
        }
    }
}