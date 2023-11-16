package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class AddViewModelTest {

    @Test
    fun test() {
        val order = Order()
        val repository = FakeAddRepository.Base(order)
        val liveDataWrapper = FakeAddLiveDataWrapper.Base(order)
        val clear = FakeClearViewModel.Base(order)
        val viewModel =
            AddViewModel(
                repository = repository,
                liveDataWrapper = liveDataWrapper,
                clear = clear,
                dispatcher = Dispatchers.Unconfined
            )

        viewModel.add(value = "new text input")

        repository.checkAddCalled("new text input")
        liveDataWrapper.checkAddCalled("new text input")
        clear.checkClearCalled(AddViewModel::class.java)

        order.checkCallsList(listOf(REPOSITORY_ADD, LIVEDATA_ADD, CLEAR))
    }
}

private class Order {

    private val list = mutableListOf<String>()

    fun add(value: String) {
        list.add(value)
    }

    fun checkCallsList(expected: List<String>) {
        assertEquals(expected, list)
    }
}

private interface FakeClearViewModel : ClearViewModel {

    fun checkClearCalled(expected: Class<out ViewModel>)

    class Base(private val order: Order) : FakeClearViewModel {

        private lateinit var actual: Class<out ViewModel>

        override fun checkClearCalled(expected: Class<out ViewModel>) {
            assertEquals(expected, actual)
        }

        override fun clearViewModel(clasz: Class<out ViewModel>) {
            order.add(CLEAR)
            actual = clasz
        }
    }
}

private interface FakeAddLiveDataWrapper : ListLiveDataWrapper.Add {

    fun checkAddCalled(expected: String)

    class Base(private val order: Order) : FakeAddLiveDataWrapper {

        private var actual = ""

        override fun checkAddCalled(expected: String) {
            assertEquals(expected, actual)
        }

        override fun add(value: String) {
            order.add(LIVEDATA_ADD)
            actual = value
        }
    }
}

private interface FakeAddRepository : Repository.Add {

    fun checkAddCalled(expected: String)

    class Base(private val order: Order) : FakeAddRepository {

        private var actual = ""

        override fun checkAddCalled(expected: String) {
            assertEquals(expected, actual)
        }

        override fun add(value: String) {
            order.add(REPOSITORY_ADD)
            actual = value
        }
    }
}

private const val REPOSITORY_ADD = "repository#add"
private const val LIVEDATA_ADD = "liveData#add"
private const val CLEAR = "clearCall"