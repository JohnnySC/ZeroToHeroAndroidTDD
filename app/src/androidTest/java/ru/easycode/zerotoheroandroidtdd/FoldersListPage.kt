package ru.easycode.zerotoheroandroidtdd

import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers.allOf

class FoldersListPage {

    private val rootId: Int = R.id.foldersRootLayout
    private val recyclerViewMatcher = RecyclerViewMatcher(R.id.foldersRecyclerView)

    private val title = onView(
        allOf(
            withText("Folders"),
            isAssignableFrom(TextView::class.java),
            withId(R.id.foldersTitleTextView),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(rootId))
        )
    )

    fun checkVisibleNow() {
        title.check(matches(isDisplayed()))
    }

    fun clickAddButton() {
        onView(
            allOf(
                withId(R.id.addButton),
                isAssignableFrom(FloatingActionButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(rootId))
            )
        ).perform(click())
    }

    fun checkNotVisibleNow() {
        title.check(doesNotExist())
    }

    fun checkFolder(position: Int, title: String, count: String) {
        val folderLinearLayout: Int = R.id.folderLinearLayout
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(withId(folderLinearLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                recyclerViewMatcher.atPosition(position, R.id.folderTitleTextView)
            )
        ).check(matches(withText(title)))

        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(withId(folderLinearLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                recyclerViewMatcher.atPosition(position, R.id.folderCountTextView)
            )
        ).check(matches(withText(count)))
    }

    fun clickFolderAt(position: Int) {
        onView(recyclerViewMatcher.atPosition(position)).perform(click())
    }
}