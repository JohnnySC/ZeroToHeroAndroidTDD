package ru.easycode.zerotoheroandroidtdd.note.core

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.folder.core.FakeNow

class NotesRepositoryTest {

    @Test
    fun test() = runBlocking {
        val now = FakeNow.Base(15L)
        val dao = FakeNotesDao.Base()
        val repository = NotesRepository.Base(
            now = now,
            dao = dao
        )

        repository.createNote(folderId = 1L, text = "first note")
        repository.createNote(folderId = 1L, text = "second note")
        repository.createNote(folderId = 2L, text = "third note")
        repository.createNote(folderId = 2L, text = "forth note")
        repository.createNote(folderId = 2L, text = "fifth note")

        val notesFirstFolderInitialActual = repository.noteList(folderId = 1L)
        val notesFirstFolderInitialExpected: List<MyNote> = listOf(
            MyNote(id = 15L, title = "first note", folderId = 1L),
            MyNote(id = 16L, title = "second note", folderId = 1L)
        )
        assertEquals(notesFirstFolderInitialExpected, notesFirstFolderInitialActual)
        val notesSecondFolderInitialActual = repository.noteList(folderId = 2L)
        val notesSecondFolderInitialExpected: List<MyNote> = listOf(
            MyNote(id = 17L, title = "third note", folderId = 2L),
            MyNote(id = 18L, title = "forth note", folderId = 2L),
            MyNote(id = 19L, title = "fifth note", folderId = 2L),
        )
        assertEquals(notesSecondFolderInitialExpected, notesSecondFolderInitialActual)

        repository.deleteNote(15L)
        repository.deleteNote(18L)

        repository.renameNote(16L, "new name for 2")
        repository.renameNote(19L, "new name for last one")

        val expectedNote = MyNote(id = 19L, title = "new name for last one", folderId = 2L)
        val actualNote: MyNote = repository.note(noteId = 19L)
        assertEquals(expectedNote, actualNote)

        val firstFolderFinalActualList = repository.noteList(1L)
        val firstFolderFinalExpectedList = listOf(
            MyNote(id = 16L, title = "new name for 2", folderId = 1L)
        )
        assertEquals(firstFolderFinalExpectedList, firstFolderFinalActualList)

        val secondFolderFinalActualList = repository.noteList(2L)
        val secondFolderFinalExpectedList = listOf(
            MyNote(id = 17L, title = "third note", folderId = 2L),
            MyNote(id = 19L, title = "new name for last one", folderId = 2L),
        )
        assertEquals(secondFolderFinalExpectedList, secondFolderFinalActualList)
    }
}

interface FakeNotesDao : NotesDao {

    class Base : FakeNotesDao {

        private val set = HashSet<NoteCache>()

        override suspend fun notes(folderId: Long): List<NoteCache> {
            return set.filter { it.folderId == folderId }.toList()
        }

        override suspend fun note(noteId: Long): NoteCache {
            return set.find { it.id == noteId }!!
        }

        override suspend fun insert(note: NoteCache) {
            set.find { it.id == note.id }?.let {
                set.remove(it)
            }
            set.add(note)
        }

        override suspend fun delete(noteId: Long) {
            set.find { it.id == noteId }?.let {
                set.remove(it)
            }
        }
    }
}