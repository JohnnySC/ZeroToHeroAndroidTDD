package ru.easycode.zerotoheroandroidtdd

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task026Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_add() {
        val mainPage = MainPage()
        mainPage.checkVisibleNow()
        mainPage.clickAddButton()

        val addPage = AddPage()
        addPage.checkVisibleNow()
        addPage.inputText("first item in the list")
        addPage.clickSaveButton()
        addPage.checkNotVisibleNow()

        mainPage.checkItem(position = 0, text = "first item in the list")

        mainPage.clickAddButton()
        addPage.checkVisibleNow()
        addPage.inputText("second item in the list")
        addPage.clickSaveButton()
        addPage.checkNotVisibleNow()
        mainPage.checkItem(position = 1, text = "second item in the list")

        activityScenarioRule.scenario.recreate()

        mainPage.checkItem(position = 0, text = "first item in the list")
        mainPage.checkItem(position = 1, text = "second item in the list")
    }
}