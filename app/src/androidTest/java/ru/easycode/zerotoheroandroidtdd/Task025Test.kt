package ru.easycode.zerotoheroandroidtdd

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.easycode.zerotoheroandroidtdd.main.MainActivity

/**
 * Instructions to go through this task:
 *
 * 1. Please start with MainViewModelTest
 * @see ru.easycode.zerotoheroandroidtdd.main.MainViewModelTest
 *
 * 2. Then please solve ViewModelFactoryTest
 * @see ru.easycode.zerotoheroandroidtdd.core.ViewModelFactoryTest
 *
 * 3. Then need to solve ListViewModelTest#test_navigation
 * @see ru.easycode.zerotoheroandroidtdd.list.ListViewModelTest
 *
 * 4. So now you can solve 4 tests here
 * @see test_navigate_to_create_screen
 * @see test_navigate_to_create_screen_and_comeback
 * @see test_input_not_enough_text_and_comeback
 * @see test_input_enough_text_and_comeback
 *
 * 5. To go forward you will need to solve CreateViewModelTest
 * @see ru.easycode.zerotoheroandroidtdd.create.CreateViewModelTest
 *
 * 6.So now you can go with next 2 tests here
 * @see test_create_one_item
 * @see test_create_many_items
 *
 * 7. Before the last test here you will need to solve ListViewModelTest#test_save_and_restore
 * @see ru.easycode.zerotoheroandroidtdd.list.ListViewModelTest
 *
 * 8. And finally you can solve the last test
 * @see test_save_and_restore
 */
@RunWith(AndroidJUnit4::class)
class Task025Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_navigate_to_create_screen() {
        val listPage = ListPage()
        listPage.checkVisibleNow()
        listPage.clickAddButton()

        val createPage = CreatePage()
        createPage.checkVisibleNow()
    }

    @Test
    fun test_navigate_to_create_screen_and_comeback() {
        val listPage = ListPage()
        listPage.checkVisibleNow()
        listPage.clickAddButton()

        val createPage = CreatePage()
        createPage.checkVisibleNow()
        createPage.checkButtonNotEnabled()

        pressBack()
        createPage.checkNotVisibleNow()
        listPage.checkVisibleNow()
    }

    @Test
    fun test_input_not_enough_text_and_comeback() {
        val listPage = ListPage()
        listPage.checkVisibleNow()
        listPage.clickAddButton()

        val createPage = CreatePage()
        createPage.checkVisibleNow()
        createPage.checkButtonNotEnabled()
        createPage.inputText("12")
        createPage.checkButtonNotEnabled()

        pressBack()
        createPage.checkNotVisibleNow()
        listPage.checkVisibleNow()
    }

    @Test
    fun test_input_enough_text_and_comeback() {
        val listPage = ListPage()
        listPage.checkVisibleNow()
        listPage.clickAddButton()

        val createPage = CreatePage()
        createPage.checkVisibleNow()
        createPage.checkButtonNotEnabled()
        createPage.inputText("123")
        createPage.checkButtonEnabled()

        pressBack()
        createPage.checkNotVisibleNow()
        listPage.checkVisibleNow()
    }

    @Test
    fun test_create_one_item() {
        val listPage = ListPage()
        listPage.checkVisibleNow()
        listPage.clickAddButton()

        val createPage = CreatePage()
        createPage.checkVisibleNow()
        createPage.checkButtonNotEnabled()
        createPage.inputText("one")
        createPage.clickCreateButton()

        createPage.checkNotVisibleNow()
        listPage.checkVisibleNow()
        listPage.checkTextAtPosition(0, "one")
    }

    @Test
    fun test_create_many_items() {
        val listPage = ListPage()
        val createPage = CreatePage()

        for (i in 0..10) {
            val text = "testing text number $i"
            listPage.checkVisibleNow()
            listPage.clickAddButton()
            createPage.checkVisibleNow()
            createPage.checkButtonNotEnabled()
            createPage.inputText(text)
            createPage.clickCreateButton()
            createPage.checkNotVisibleNow()
            listPage.checkVisibleNow()
            listPage.checkTextAtPosition(i, text)
        }
    }

    @Test
    fun test_save_and_restore() {
        val listPage = ListPage()
        test_create_many_items()
        val texts = (0..10).map { "testing text number $it" }
        listPage.checkTexts(texts)

        activityScenarioRule.scenario.recreate()
        listPage.checkTexts(texts)
    }
}