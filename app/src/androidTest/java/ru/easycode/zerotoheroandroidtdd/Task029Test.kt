package ru.easycode.zerotoheroandroidtdd

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.easycode.zerotoheroandroidtdd.main.MainActivity

@RunWith(AndroidJUnit4::class)
class Task029Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun create_folder_create_note() {
        val foldersListPage = FoldersListPage()
        foldersListPage.checkVisibleNow()

        foldersListPage.clickAddButton()
        foldersListPage.checkNotVisibleNow()
        val createFolderPage = CreateFolderPage()
        createFolderPage.checkVisibleNow()

        createFolderPage.inputFolderName(text = "first folder")

        createFolderPage.clickSaveButton()
        createFolderPage.checkNotVisibleNow()
        foldersListPage.checkVisibleNow()
        foldersListPage.checkFolder(position = 0, title = "first folder", count = "0")

        foldersListPage.clickFolderAt(position = 0)
        foldersListPage.checkNotVisibleNow()
        val folderDetailsPage = FolderDetailsPage()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "0")

        folderDetailsPage.clickAddButton()
        folderDetailsPage.checkNotVisibleNow()
        val createNotePage = CreateNotePage()
        createNotePage.checkVisibleNow()

        createNotePage.inputNote(text = "note 1 in folder 1")

        createNotePage.clickSaveButton()
        createNotePage.checkNotVisibleNow()
        folderDetailsPage.checkNote(position = 0, title = "note 1 in folder 1")
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")

        Espresso.pressBack()
        folderDetailsPage.checkNotVisibleNow()
        foldersListPage.checkVisibleNow()
        foldersListPage.checkFolder(position = 0, title = "first folder", count = "1")
    }

    @Test
    fun create_folder_create_note_delete_folder() {
        create_folder_create_note()
        val foldersListPage = FoldersListPage()
        foldersListPage.checkVisibleNow()

        foldersListPage.clickFolderAt(position = 0)
        foldersListPage.checkNotVisibleNow()
        val folderDetailsPage = FolderDetailsPage()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")

        folderDetailsPage.clickEditFolderButton()
        folderDetailsPage.checkNotVisibleNow()
        val editFolderPage = EditFolderPage()
        editFolderPage.checkVisibleNow(text = "first folder")

        editFolderPage.clickDeleteButton()
        editFolderPage.checkNotVisibleNow()
        foldersListPage.checkVisibleNow()

        foldersListPage.clickAddButton()
        foldersListPage.checkNotVisibleNow()
        val createFolderPage = CreateFolderPage()
        createFolderPage.checkVisibleNow()

        createFolderPage.inputFolderName(text = "new first folder")

        createFolderPage.clickSaveButton()
        createFolderPage.checkNotVisibleNow()
        foldersListPage.checkVisibleNow()
        foldersListPage.checkFolder(position = 0, title = "new first folder", count = "0")
    }

    @Test
    fun create_folder_create_note_rename_folder() {
        create_folder_create_note()
        val foldersListPage = FoldersListPage()
        foldersListPage.checkVisibleNow()

        foldersListPage.clickFolderAt(position = 0)
        foldersListPage.checkNotVisibleNow()
        val folderDetailsPage = FolderDetailsPage()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")

        folderDetailsPage.clickEditFolderButton()
        folderDetailsPage.checkNotVisibleNow()
        val editFolderPage = EditFolderPage()
        editFolderPage.checkVisibleNow(text = "first folder")

        editFolderPage.replaceText(text = "new folder name")
        editFolderPage.clickSaveButton()
        editFolderPage.checkNotVisibleNow()
        folderDetailsPage.checkVisibleNow(title = "new folder name", count = "1")
    }

    @Test
    fun create_folder_create_note_rename_note() {
        create_folder_create_note()
        val foldersListPage = FoldersListPage()
        foldersListPage.checkVisibleNow()

        foldersListPage.clickFolderAt(position = 0)
        foldersListPage.checkNotVisibleNow()
        val folderDetailsPage = FolderDetailsPage()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")

        folderDetailsPage.clickNote(position = 0)
        folderDetailsPage.checkNotVisibleNow()

        val editNotePage = EditNotePage()
        editNotePage.checkVisibleNow(text = "note 1 in folder 1")

        editNotePage.replaceText(text = "note 1 new name")

        editNotePage.clickSaveButton()
        editNotePage.checkNotVisibleNow()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")
        folderDetailsPage.checkNote(position = 0, title = "note 1 new name")
    }

    @Test
    fun create_folder_create_note_delete_note() {
        create_folder_create_note()
        val foldersListPage = FoldersListPage()
        foldersListPage.checkVisibleNow()

        foldersListPage.clickFolderAt(position = 0)
        foldersListPage.checkNotVisibleNow()
        val folderDetailsPage = FolderDetailsPage()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")

        folderDetailsPage.clickNote(position = 0)
        folderDetailsPage.checkNotVisibleNow()

        val editNotePage = EditNotePage()
        editNotePage.checkVisibleNow(text = "note 1 in folder 1")

        editNotePage.clickDeleteButton()
        editNotePage.checkNotVisibleNow()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "0")

        folderDetailsPage.clickAddButton()
        folderDetailsPage.checkNotVisibleNow()
        val createNotePage = CreateNotePage()
        createNotePage.checkVisibleNow()

        createNotePage.inputNote(text = "new note after delete")

        createNotePage.clickSaveButton()
        createNotePage.checkNotVisibleNow()
        folderDetailsPage.checkVisibleNow(title = "first folder", count = "1")
        folderDetailsPage.checkNote(position = 0, title = "new note after delete")
    }
}