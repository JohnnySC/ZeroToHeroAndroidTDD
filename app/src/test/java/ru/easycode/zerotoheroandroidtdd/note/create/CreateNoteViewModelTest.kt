package ru.easycode.zerotoheroandroidtdd.note.create

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeClear
import ru.easycode.zerotoheroandroidtdd.core.FakeClear.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation.Companion.NAVIGATE
import ru.easycode.zerotoheroandroidtdd.core.Order
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository

class CreateNoteViewModelTest {

    private lateinit var order: Order
    private lateinit var repository: FakeCreateNoteRepository
    private lateinit var addLiveDataWrapper: FakeAddNoteLiveDataWrapper
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear
    private lateinit var viewModel: CreateNoteViewModel

    @Before
    fun setup() {
        order = Order()
        repository = FakeCreateNoteRepository.Base(order, 101)
        addLiveDataWrapper = FakeAddNoteLiveDataWrapper.Base(order)
        viewModel = CreateNoteViewModel(
            addLiveDataWrapper = addLiveDataWrapper,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_create() {
        viewModel.createNote(folderId = 4L, text = "new note text")

        repository.check(4L, "new note text")
        addLiveDataWrapper.check(NoteUi(id = 101, title = "new note text", folderId = 4L))
        clear.check(listOf(CreateNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CREATE_NOTE_REPOSITORY, NOTE_LIVEDATA_ADD, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(CreateNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CLEAR, NAVIGATE))
    }

}

private const val NOTE_LIVEDATA_ADD = "NoteListLiveDataWrapper.Create#"
private const val CREATE_NOTE_REPOSITORY = "NotesRepository.Create#createNote"

private interface FakeAddNoteLiveDataWrapper : NoteListLiveDataWrapper.Create {

    fun check(expected: NoteUi)

    class Base(private val order: Order) : FakeAddNoteLiveDataWrapper {

        private lateinit var actual: NoteUi

        override fun check(expected: NoteUi) {
            assertEquals(expected, actual)
        }

        override fun create(noteUi: NoteUi) {
            actual = noteUi
            order.add(NOTE_LIVEDATA_ADD)
        }
    }
}

private interface FakeCreateNoteRepository : NotesRepository.Create {

    fun check(folderId: Long, text: String)

    class Base(private val order: Order, private var noteId: Long) : FakeCreateNoteRepository {

        private var actualFolderId: Long = -1
        private var actualText = ""

        override fun check(folderId: Long, text: String) {
            assertEquals(folderId, actualFolderId)
            assertEquals(text, actualText)
        }

        override suspend fun createNote(folderId: Long, text: String): Long {
            order.add(CREATE_NOTE_REPOSITORY)
            return MyNote(id = noteId++, title = text, folderId = folderId)
        }
    }
}