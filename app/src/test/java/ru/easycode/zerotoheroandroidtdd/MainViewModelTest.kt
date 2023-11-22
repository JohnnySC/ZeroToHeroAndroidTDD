package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.Dispatchers
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test() {
        val repository = FakeRepository.Base()
        val liveDataWrapper = FakeListLiveDataWrapper.Base()
        val viewModel = MainViewModel(
            repository = repository, liveDataWrapper = liveDataWrapper,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )

        repository.expectList(listOf(Item(id = 0L, text = "1"), Item(id = 1L, text = "2")))

        viewModel.init()

        liveDataWrapper.checkUpdateCallList(
            listOf(
                ItemUi(id = 0L, text = "1"),
                ItemUi(id = 1L, text = "2")
            )
        )
    }
}

private interface FakeRepository : Repository.Read {

    fun expectList(list: List<Item>)

    class Base : FakeRepository {

        private val list = mutableListOf<Item>()

        override fun expectList(list: List<Item>) {
            this.list.addAll(list)
        }

        override fun list(): List<Item> {
            return list
        }
    }
}