package ru.easycode.zerotoheroandroidtdd

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.Matchers.allOf

class ListPage : AbstractPage(R.id.listFrameLayout) {

    fun checkTexts(list: List<String>) {
        list.forEachIndexed { index, text ->
            checkTextAtPosition(index, text)
        }
    }

    fun checkTextAtPosition(position: Int, text: String) {
        onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(position, R.id.elementTextView))
            .check(matches(withText(text)))
    }

    fun clickAddButton() {
        onView(
            allOf(
                withParent(withId(root)),
                isAssignableFrom(FloatingActionButton::class.java),
                withId(R.id.addButton)
            )
        ).perform(click())
    }
}