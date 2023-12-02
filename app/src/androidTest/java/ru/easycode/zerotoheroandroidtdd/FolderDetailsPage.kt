package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers.allOf

class FolderDetailsPage {

    private val rootId: Int = R.id.folderDetailsRootLayout
    private val titleView = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withId(R.id.folderNameTextView),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(rootId))
        )
    )
    private val recyclerViewMatcher = RecyclerViewMatcher(R.id.notesRecyclerView)

    fun checkVisibleNow(title: String, count: String) {
        titleView.check(matches(withText(title)))

        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.notesCountTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(rootId))
            )
        ).check(matches(withText(count)))
    }

    fun clickAddButton() {
        onView(
            allOf(
                isAssignableFrom(FloatingActionButton::class.java),
                withId(R.id.addNoteButton),
                withParent(withId(rootId)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).perform(click())
    }

    fun checkNotVisibleNow() {
        titleView.check(doesNotExist())
    }

    fun checkNote(position: Int, title: String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(withId(rootId)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                recyclerViewMatcher.atPosition(position, R.id.noteTitleTextView)
            )
        ).check(matches(withText(title)))
    }

    fun clickEditFolderButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(R.id.editFolderButton),
                withParent(withId(rootId)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText("edit folder")
            )
        ).perform(click())
    }

    fun clickNote(position: Int) {
        onView(
            allOf(
                withParent(withId(rootId)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                recyclerViewMatcher.atPosition(position)
            )
        ).perform(click())
    }
}