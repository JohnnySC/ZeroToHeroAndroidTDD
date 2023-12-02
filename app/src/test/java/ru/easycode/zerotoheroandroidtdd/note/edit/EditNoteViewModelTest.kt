package ru.easycode.zerotoheroandroidtdd.note.edit

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeClear
import ru.easycode.zerotoheroandroidtdd.core.FakeClear.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation.Companion.NAVIGATE
import ru.easycode.zerotoheroandroidtdd.core.Order

class EditNoteViewModelTest {

    private lateinit var order: Order
    private lateinit var repository: FakeEditNoteRepository
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear
    private lateinit var noteLiveDataWrapper: FakeNoteLiveDataWrapper
    private lateinit var noteListLiveDataWrapper: FakeNoteListLiveDataWrapper
    private lateinit var folderLiveDataWrapper: FakeDecrementFoldersNoteCountLiveDataWrapper
    private lateinit var viewModel: EditNoteViewModel

    @Before
    fun setup() {
        order = Order()
        repository = FakeEditNoteRepository.Base(order)
        clear = FakeClear.Base(order)
        navigation = FakeNavigation.Base(order)
        noteLiveDataWrapper = FakeNoteLiveDataWrapper.Base(order)
        folderLiveDataWrapper = FakeDecrementFoldersNoteCountLiveDataWrapper.Base(order)
        noteListLiveDataWrapper = FakeNoteListLiveDataWrapper.Base(order)
        viewModel = EditNoteViewModel(
            folderLiveDataWrapper = folderLiveDataWrapper,
            noteLiveDataWrapper = noteLiveDataWrapper,
            noteListLiveDataWrapper = noteLiveDataWrapper,
            repository = repository,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        viewModel.init(noteId = 32L)

        repository.checkNote(32L)
        noteLiveDataWrapper.check("first note")
        order.check(listOf(REPOSITORY_NOTE, NOTE_LIVE_DATA))
    }

    @Test
    fun test_delete() {
        viewModel.deleteNote(noteId = 32L)

        repository.checkDelete(32L)
        clear.check(listOf(EditNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(REPOSITORY_DELETE, DECREMENT_COUNT_LIVEDATA, CLEAR, NAVIGATE))
    }

    @Test
    fun test_rename() {
        viewModel.renameNote(noteId = 33L, newText = "a new text")

        repository.checkRename(33L, "a new text")
        noteListLiveDataWrapper.check(33L, "a new text")
        clear.check(listOf(EditNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(REPOSITORY_RENAME, NOTES_LIVE_DATA_UPDATE, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(EditNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}

private const val NOTE_LIVE_DATA = "NoteLiveDataWrapper#UPDATE"
private const val DECREMENT_COUNT_LIVEDATA = "FolderLiveDataWrapper.Rename#rename"
private const val REPOSITORY_DELETE = "NotesRepository.Edit#DELETE"
private const val REPOSITORY_RENAME = "NotesRepository.Edit#RENAME"
private const val REPOSITORY_NOTE = "NotesRepository.Edit#note"
private const val NOTES_LIVE_DATA_UPDATE = "NoteListLiveDataWrapper.Update#update"

private interface FakeNoteLiveDataWrapper : NoteLiveDataWrapper {

    fun check(expected: String)

    class Base(private val order: Order) : FakeNoteLiveDataWrapper {

        private var actual = ""

        override fun check(expected: String) {
            assertEquals(expected, actual)
        }

        override fun update(noteText: String) {
            actual = noteText
            order.add(NOTE_LIVE_DATA)
        }
    }
}

private interface FakeDecrementFoldersNoteCountLiveDataWrapper : FolderLiveDataWrapper.Decrement {

    class Base(private val order: Order) : FakeDecrementFoldersNoteCountLiveDataWrapper {

        override fun decrement() {
            order.add(DECREMENT_COUNT_LIVEDATA)
        }
    }
}

private interface FakeEditNoteRepository : NotesRepository.Edit {

    fun checkNote(expectedNoteId: Long)

    fun checkDelete(expectedNoteId: Long)

    fun checkRename(expectedNoteId: Long, expectedNewName: String)

    class Base(private val order: Order) : FakeEditNoteRepository {

        private var actualId = -1L
        private var actualName = ""

        override fun checkDelete(expectedNoteId: Long) {
            assertEquals(expectedNoteId, actualId)
        }

        override fun checkRename(expectedNoteId: Long, expectedNewName: String) {
            assertEquals(expectedNoteId, actualId)
            assertEquals(expectedNewName, actualName)
        }

        override fun checkNote(expectedNoteId: Long) {
            assertEquals(expectedNoteId, actualId)
        }

        override suspend fun deleteNote(noteId: Long) {
            actualId = noteId
            order.add(REPOSITORY_DELETE)
        }

        override suspend fun renameNote(noteId: Long, newName: String) {
            actualId = noteId
            actualName = newName
            order.add(REPOSITORY_RENAME)
        }

        override suspend fun note(noteId: Long): MyNote {
            actualId = noteId
            order.add(REPOSITORY_NOTE)
            return MyNote(id = noteId, title = "first note", folderId = 7L)
        }
    }
}

private interface FakeNoteListLiveDataWrapper : NoteListLiveDataWrapper.Update {

    fun check(expectedNoteId: Long, expectedNewText: String)

    class Base(private val order: Order) : FakeNoteListLiveDataWrapper {

        private var actualText: String = ""
        private var actualId: Long = -1

        override fun check(expectedNoteId: Long, expectedNewText: String) {
            assertEquals(expectedNoteId, actualId)
            assertEquals(expectedNewText, actualText)
        }

        override fun update(noteId: Long, newText: String) {
            actualId = noteId
            actualText = newText
            order.add(NOTES_LIVE_DATA_UPDATE)
        }
    }
}
