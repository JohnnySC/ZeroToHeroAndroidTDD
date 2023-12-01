package ru.easycode.zerotoheroandroidtdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.FakeClearViewModel.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.FakeListLiveDataWrapper.Companion.LIVE_DATA_DELETE
import ru.easycode.zerotoheroandroidtdd.FakeRepositoryDelete.Companion.REPOSITORY_DELETE

class DeleteViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var order: Order
    private lateinit var liveDataWrapper: FakeListLiveDataWrapper
    private lateinit var repository: FakeRepositoryDelete
    private lateinit var clear: FakeClearViewModel
    private lateinit var viewModel: DeleteViewModel

    @Before
    fun setup() {
        order = Order()
        liveDataWrapper = FakeListLiveDataWrapper.Base(order)
        repository = FakeRepositoryDelete.Base(order)
        clear = FakeClearViewModel.Base(order)
        viewModel = DeleteViewModel(
            deleteLiveDataWrapper = liveDataWrapper,
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
        clear.checkClearCalled(DeleteViewModel::class.java)
        order.checkCallsList(listOf(REPOSITORY_DELETE, LIVE_DATA_DELETE, CLEAR))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()
        clear.checkClearCalled(DeleteViewModel::class.java)
    }
}

private interface FakeRepositoryDelete : Repository.Delete {

    companion object {
        const val REPOSITORY_DELETE = "Repository.Delete#delete"
    }

    fun checkDeleteCalled(id: Long)

    class Base(private val order: Order = Order()) : FakeRepositoryDelete {

        private var actualId: Long = Long.MIN_VALUE

        override fun item(id: Long): Item {
            return Item(id, id.toString())
        }

        override fun checkDeleteCalled(id: Long) {
            assertEquals(id, actualId)
        }

        override fun delete(id: Long) {
            order.add(REPOSITORY_DELETE)
            actualId = id
        }
    }
}