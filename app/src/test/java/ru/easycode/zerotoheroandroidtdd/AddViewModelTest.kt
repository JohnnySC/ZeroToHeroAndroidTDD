package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.FakeClearViewModel.Companion.CLEAR

class AddViewModelTest {

    @Test
    fun test_add() {
        val order = Order()
        val repository = FakeAddRepository.Base(order)
        val liveDataWrapper = FakeAddLiveDataWrapper.Base(order)
        val clear = FakeClearViewModel.Base(order)
        val viewModel =
            AddViewModel(
                repository = repository,
                liveDataWrapper = liveDataWrapper,
                clear = clear,
                dispatcher = Dispatchers.Unconfined,
                dispatcherMain = Dispatchers.Unconfined
            )

        viewModel.add(value = "new text input")

        repository.checkAddCalled("new text input")
        liveDataWrapper.checkAddCalled(ItemUi(id = 10L, text = "new text input"))
        clear.checkClearCalled(AddViewModel::class.java)

        order.checkCallsList(listOf(REPOSITORY_ADD, LIVEDATA_ADD, CLEAR))
    }

    @Test
    fun test_comeback() {
        val order = Order()
        val repository = FakeAddRepository.Base(order)
        val liveDataWrapper = FakeAddLiveDataWrapper.Base(order)
        val clear = FakeClearViewModel.Base(order)
        val viewModel =
            AddViewModel(
                repository = repository,
                liveDataWrapper = liveDataWrapper,
                clear = clear,
                dispatcher = Dispatchers.Unconfined,
                dispatcherMain = Dispatchers.Unconfined
            )

        viewModel.comeback()

        clear.checkClearCalled(AddViewModel::class.java)

        order.checkCallsList(listOf(CLEAR))
    }
}

class Order {

    private val list = mutableListOf<String>()

    fun add(value: String) {
        list.add(value)
    }

    fun checkCallsList(expected: List<String>) {
        assertEquals(expected, list)
    }
}

private interface FakeAddLiveDataWrapper : ListLiveDataWrapper.Add {

    fun checkAddCalled(expected: ItemUi)

    class Base(private val order: Order) : FakeAddLiveDataWrapper {

        private var actual = ItemUi(Long.MIN_VALUE, "")

        override fun checkAddCalled(expected: ItemUi) {
            assertEquals(expected, actual)
        }

        override fun add(value: ItemUi) {
            order.add(LIVEDATA_ADD)
            actual = value
        }
    }
}

private interface FakeAddRepository : Repository.Add {

    fun checkAddCalled(expected: String)

    class Base(private val order: Order) : FakeAddRepository {

        private var actual = ""
        private var count = 10L

        override fun checkAddCalled(expected: String) {
            assertEquals(expected, actual)
        }

        override fun add(value: String): Long {
            order.add(REPOSITORY_ADD)
            actual = value
            return count++
        }
    }
}

private const val REPOSITORY_ADD = "repository#add"
private const val LIVEDATA_ADD = "liveData#add"