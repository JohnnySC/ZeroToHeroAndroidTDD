package ru.easycode.zerotoheroandroidtdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.FakeClearViewModel.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.FakeListLiveDataWrapper.Companion.LIVE_DATA_DELETE
import ru.easycode.zerotoheroandroidtdd.FakeListLiveDataWrapper.Companion.LIVE_DATA_UPDATE
import ru.easycode.zerotoheroandroidtdd.FakeRepositoryChange.Companion.REPOSITORY_DELETE
import ru.easycode.zerotoheroandroidtdd.FakeRepositoryChange.Companion.REPOSITORY_UPDATE

class DetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var order: Order
    private lateinit var liveDataWrapper: FakeListLiveDataWrapper
    private lateinit var repository: FakeRepositoryChange
    private lateinit var clear: FakeClearViewModel
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        order = Order()
        liveDataWrapper = FakeListLiveDataWrapper.Base(order)
        repository = FakeRepositoryChange.Base(order)
        clear = FakeClearViewModel.Base(order)
        viewModel = DetailsViewModel(
            changeLiveDataWrapper = liveDataWrapper,
            repository = repository,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        viewModel.init(itemId = 5L)
        assertEquals("5", viewModel.liveData.value)
    }

    @Test
    fun test_delete() {
        liveDataWrapper.update(listOf(ItemUi(id = 8L, text = "8"), ItemUi(id = 9L, text = "any")))
        viewModel.init(itemId = 8L)

        viewModel.delete(itemId = 8L)
        repository.checkDeleteCalled(8L)
        liveDataWrapper.checkUpdateCallList(listOf(ItemUi(id = 9L, text = "any")))
        clear.checkClearCalled(DetailsViewModel::class.java)
        order.checkCallsList(listOf(REPOSITORY_DELETE, LIVE_DATA_DELETE, CLEAR))
    }

    @Test
    fun test_update() {
        liveDataWrapper.update(listOf(ItemUi(id = 8L, text = "8"), ItemUi(id = 9L, text = "any")))
        viewModel.init(itemId = 8L)

        viewModel.update(itemId = 8L, newText = "newText")
        repository.checkUpdateCalled(8L, "newText")
        liveDataWrapper.checkUpdateCallList(
            listOf(
                ItemUi(id = 8L, text = "newText"),
                ItemUi(id = 9L, text = "any")
            )
        )
        clear.checkClearCalled(DetailsViewModel::class.java)
        order.checkCallsList(listOf(REPOSITORY_UPDATE, LIVE_DATA_UPDATE, CLEAR))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()
        clear.checkClearCalled(DetailsViewModel::class.java)
    }
}

private interface FakeRepositoryChange : Repository.Change {

    companion object {
        const val REPOSITORY_DELETE = "Repository.Delete#delete"
        const val REPOSITORY_UPDATE = "Repository.Delete#update"
    }

    fun checkDeleteCalled(id: Long)

    fun checkUpdateCalled(id: Long, newText: String)

    class Base(private val order: Order = Order()) : FakeRepositoryChange {

        private var actualId: Long = Long.MIN_VALUE
        private var updateText: String = ""

        override fun item(id: Long): Item {
            return Item(id, id.toString())
        }

        override fun checkDeleteCalled(id: Long) {
            assertEquals(id, actualId)
        }

        override fun checkUpdateCalled(id: Long, newText: String) {
            assertEquals(updateText, newText)
            assertEquals(id, actualId)
        }

        override fun delete(id: Long) {
            order.add(REPOSITORY_DELETE)
            actualId = id
        }

        override fun update(id: Long, newText: String) {
            updateText = newText
            actualId = id
            order.add(REPOSITORY_UPDATE)
        }
    }
}