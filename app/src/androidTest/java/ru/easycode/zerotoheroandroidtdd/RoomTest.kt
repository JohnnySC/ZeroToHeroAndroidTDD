package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.easycode.zerotoheroandroidtdd.core.AppDataBase
import ru.easycode.zerotoheroandroidtdd.core.FolderCache
import ru.easycode.zerotoheroandroidtdd.core.FoldersDao
import ru.easycode.zerotoheroandroidtdd.core.NoteCache
import ru.easycode.zerotoheroandroidtdd.core.NotesDao
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: AppDataBase
    private lateinit var notesDao: NotesDao
    private lateinit var foldersDao: FoldersDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        notesDao = db.notesDao()
        foldersDao = db.foldersDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_folders() = runBlocking {
        foldersDao.insert(folder = FolderCache(id = 1L, text = "first folder"))
        foldersDao.insert(folder = FolderCache(id = 2L, text = "second folder"))

        val initialActual = foldersDao.folders()
        val initialExpected = listOf(
            FolderCache(id = 1L, text = "first folder"),
            FolderCache(id = 2L, text = "second folder")
        )
        assertEquals(initialExpected, initialActual)

        foldersDao.insert(folder = FolderCache(id = 1L, text = "renamed folder 1"))

        foldersDao.delete(folderId = 2L)

        val finalActual = foldersDao.folders()
        val finalExpected = listOf(
            FolderCache(id = 1L, text = "renamed folder 1"),
        )
        assertEquals(finalExpected, finalActual)
    }

    @Test
    fun test_notes() = runBlocking {
        notesDao.insert(note = NoteCache(id = 1L, text = "first note", folderId = 10L))
        notesDao.insert(note = NoteCache(id = 2L, text = "second note", folderId = 11L))
        notesDao.insert(note = NoteCache(id = 3L, text = "third note", folderId = 10L))
        notesDao.insert(note = NoteCache(id = 4L, text = "forth note", folderId = 11L))
        notesDao.insert(note = NoteCache(id = 5L, text = "fifth note", folderId = 10L))

        val firstFolderNotesExpected = listOf(
            NoteCache(id = 1L, text = "first note", folderId = 10L),
            NoteCache(id = 3L, text = "third note", folderId = 10L),
            NoteCache(id = 5L, text = "fifth note", folderId = 10L)
        )
        val firstFolderNotesActual = notesDao.notes(folderId = 10L)
        assertEquals(firstFolderNotesExpected, firstFolderNotesActual)

        val secondFolderNotesExpected = listOf(
            NoteCache(id = 2L, text = "second note", folderId = 11L),
            NoteCache(id = 4L, text = "forth note", folderId = 11L)
        )
        val secondFolderNotesActual = notesDao.notes(folderId = 11L)
        assertEquals(secondFolderNotesExpected, secondFolderNotesActual)

        notesDao.delete(noteId = 1L)
        notesDao.delete(noteId = 4L)

        notesDao.insert(note = NoteCache(id = 2L, text = "new name for note 2", folderId = 11L))
        notesDao.insert(note = NoteCache(id = 5L, text = "new name for note 5", folderId = 10L))

        val expectedRenamedOne = NoteCache(id = 2L, text = "new name for note 2", folderId = 11L)
        val expectedRenamedTwo = NoteCache(id = 5L, text = "new name for note 5", folderId = 10L)

        val actualRenamedOne = notesDao.note(noteId = 2L)
        val actualRenamedTwo = notesDao.note(noteId = 5L)

        assertEquals(expectedRenamedOne, actualRenamedOne)
        assertEquals(expectedRenamedTwo, actualRenamedTwo)

        val expectedFinalListFolderOne = listOf(
            NoteCache(id = 3L, text = "third note", folderId = 10L),
            NoteCache(id = 5L, text = "new name for note 5", folderId = 10L)
        )
        val expectedFinalListFolderTwo = listOf(
            NoteCache(id = 2L, text = "new name for note 2", folderId = 11L),
        )
        val actualFinalListFolderOne = notesDao.notes(folderId = 10L)
        val actualFinalListFolderTwo = notesDao.notes(folderId = 11L)

        assertEquals(expectedFinalListFolderOne, actualFinalListFolderOne)
        assertEquals(expectedFinalListFolderTwo, actualFinalListFolderTwo)
    }
}