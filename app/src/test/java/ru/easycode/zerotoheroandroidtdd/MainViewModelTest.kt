package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test() {
        val repository = FakeRepository.Base()
        val liveDataWrapper = FakeListLiveDataWrapper.Base()
        val viewModel = MainViewModel(
            repository = repository, liveDataWrapper = liveDataWrapper,
            dispatcher = Dispatchers.Unconfined
        )

        repository.expectList(listOf("1", "2"))

        viewModel.init()

        liveDataWrapper.checkUpdateCallList(listOf("1", "2"))
    }
}

private interface FakeListLiveDataWrapper : ListLiveDataWrapper.Mutable {

    fun checkUpdateCallList(expected: List<String>)

    class Base : FakeListLiveDataWrapper {

        private val actual = mutableListOf<String>()

        override fun checkUpdateCallList(expected: List<String>) {
            assertEquals(expected, actual)
        }

        override fun update(value: List<String>) {
            actual.addAll(value)
        }

        override fun liveData(): LiveData<List<String>> {
            throw IllegalStateException("not used")
        }
    }
}

private interface FakeRepository : Repository.Read {

    fun expectList(list: List<String>)

    class Base : FakeRepository {

        private val list = mutableListOf<String>()

        override fun expectList(list: List<String>) {
            this.list.addAll(list)
        }

        override fun list(): List<String> {
            return list
        }
    }
}