package ru.easycode.zerotoheroandroidtdd.folder.create

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeClear
import ru.easycode.zerotoheroandroidtdd.core.FakeClear.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation.Companion.NAVIGATE
import ru.easycode.zerotoheroandroidtdd.core.Order

class CreateFolderViewModelTest {

    private lateinit var order: Order
    private lateinit var liveDataWrapper: FakeAddLiveDataWrapper
    private lateinit var navigation: FakeNavigation
    private lateinit var repository: FakeCreateRepository
    private lateinit var clear: FakeClear
    private lateinit var viewModel: CreateFolderViewModel

    @Before
    fun setup() {
        order = Order()
        clear = FakeClear.Base(order)
        repository = FakeCreateRepository.Base(order, 45678)
        navigation = FakeNavigation.Base(order)
        liveDataWrapper = FakeAddLiveDataWrapper.Base(order)
        viewModel = CreateFolderViewModel(
            repository = repository,
            navigation = navigation,
            dispatcher = Dispatchers.Unconfined,
            disatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_create() {
        viewModel.createFolder(name = "some folder name")

        repository.checkCreate("some folder name")
        liveDataWrapper.check(FolderUi(id = 45678, title = "some folder name", notesCount = 0))
        navigation.checkScreen(Screen.Pop)
        clear.check(listOf(CreateFolderViewModel::class.java))

        order.check(listOf(REPOSITORY_CREATE, LIVEDATA_CREATE, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(CreateFolderViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}

private const val REPOSITORY_CREATE = "FoldersRepository.Create#createFolder"
private const val LIVEDATA_CREATE = "FolderListLiveDataWrapper.Create"

private interface FakeCreateRepository : FoldersRepository.Create {

    fun checkCreate(expectedName: String)

    class Base(
        private val order: Order,
        private var count: Long
    ) : FakeCreateRepository {

        private var actualName = ""

        override fun checkCreate(expectedName: String) {
            assertEquals(expectedName, actualName)
        }

        override suspend fun createFolder(name: String): Long {
            actualName = name
            order.add(REPOSITORY_CREATE)
            return count++
        }
    }
}

private interface FakeAddLiveDataWrapper : FolderListLiveDataWrapper.Create {

    fun check(expected: FolderUi)

    class Base(private val order: Order) : FakeAddLiveDataWrapper {

        private lateinit var actual: FolderUi

        override fun create(folderUi: FolderUi) {
            actual = folderUi
            order.add(LIVEDATA_CREATE)
        }

        override fun check(expected: FolderUi) {
            assertEquals(expected, actual)
        }
    }
}