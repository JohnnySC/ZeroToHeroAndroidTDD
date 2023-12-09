package ru.easycode.zerotoheroandroidtdd.folder.details

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeClear
import ru.easycode.zerotoheroandroidtdd.core.FakeClear.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation.Companion.NAVIGATE
import ru.easycode.zerotoheroandroidtdd.core.Order
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderScreen
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.note.core.MyNote
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteScreen
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteScreen

class FolderDetailsViewModelTest {

    private lateinit var viewModel: FolderDetailsViewModel
    private lateinit var clear: FakeClear
    private lateinit var noteListRepository: FakeNoteListRepository
    private lateinit var folderLiveDataWrapper: FakeFolderLiveDataWrapper
    private lateinit var noteListLiveDataWrapper: FakeNoteListLiveDataWrapper
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var order: Order

    @Before
    fun setup() {
        order = Order()
        clear = FakeClear.Base(order)
        noteListRepository = FakeNoteListRepository.Base(order)
        noteListLiveDataWrapper = FakeNoteListLiveDataWrapper.Base(order)
        folderLiveDataWrapper = FakeFolderLiveDataWrapper.Base(order)
        navigation = FakeNavigation.Base(order)
        viewModel = FolderDetailsViewModel(
            noteListRepository = noteListRepository,
            liveDataWrapper = noteListLiveDataWrapper,
            folderLiveDataWrapper = folderLiveDataWrapper,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        folderLiveDataWrapper.update(FolderUi(id = 7L, "folder title", 2))
        noteListRepository.expectNotes(
            listOf(
                MyNote(id = 1L, title = "first note", folderId = 7L),
                MyNote(id = 2L, title = "second note", folderId = 7L)
            )
        )

        viewModel.init()
        noteListLiveDataWrapper.check(
            listOf(
                NoteUi(id = 1L, title = "first note", folderId = 7L),
                NoteUi(id = 2L, title = "second note", folderId = 7L)
            )
        )
        noteListRepository.checkFolderId(7L)

        order.check(listOf(UPDATE_FOLDER_LIVEDATA, NOTES_REPOSITORY_READ, UPDATE_NOTES_LIVEDATA))
    }

    @Test
    fun test_create_note() {
        folderLiveDataWrapper.update(FolderUi(id = 9L, "folder title", 0))
        viewModel.createNote()
        navigation.checkScreen(CreateNoteScreen(folderId = 9L))
        order.check(listOf(UPDATE_FOLDER_LIVEDATA, NAVIGATE))
    }

    @Test
    fun test_edit_note() {
        folderLiveDataWrapper.update(FolderUi(id = 9L, "folder title", 0))
        viewModel.editNote(NoteUi(id = 15L, title = "old", folderId = 9L))
        navigation.checkScreen(EditNoteScreen(noteId = 15L))
        order.check(listOf(UPDATE_FOLDER_LIVEDATA, NAVIGATE))
    }

    @Test
    fun test_edit_folder() {
        folderLiveDataWrapper.update(FolderUi(id = 9L, "folder title", 0))
        viewModel.editFolder()
        navigation.checkScreen(EditFolderScreen(folderId = 9L))
        order.check(listOf(UPDATE_FOLDER_LIVEDATA, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()
        clear.check(listOf(FolderDetailsViewModel::class.java))
        navigation.checkScreen(FoldersListScreen)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}

private const val NOTES_REPOSITORY_READ = "NotesRepository.ReadList#noteList"
private const val UPDATE_NOTES_LIVEDATA = "NoteListLiveDataWrapper.UpdateListAndRead#update"

private interface FakeNoteListRepository : NotesRepository.ReadList {

    fun expectNotes(list: List<MyNote>)

    fun checkFolderId(expected: Long)

    class Base(private val order: Order) : FakeNoteListRepository {

        private var actualFolderId: Long = -1

        private val actual = mutableListOf<MyNote>()

        override fun expectNotes(list: List<MyNote>) {
            actual.clear()
            actual.addAll(list)
        }

        override suspend fun noteList(folderId: Long): List<MyNote> {
            actualFolderId = folderId
            order.add(NOTES_REPOSITORY_READ)
            return actual
        }

        override fun checkFolderId(expected: Long) {
            assertEquals(expected, actualFolderId)
        }
    }
}

private interface FakeNoteListLiveDataWrapper : NoteListLiveDataWrapper.UpdateListAndRead {

    fun check(expected: List<NoteUi>)

    class Base(private val order: Order) : FakeNoteListLiveDataWrapper {

        private val actual = mutableListOf<NoteUi>()

        override fun update(notes: List<NoteUi>) {
            actual.clear()
            actual.addAll(notes)
            order.add(UPDATE_NOTES_LIVEDATA)
        }

        override fun check(expected: List<NoteUi>) {
            assertEquals(expected, actual)
        }
    }
}