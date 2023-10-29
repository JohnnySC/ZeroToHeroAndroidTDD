package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test() {
        val repository = FakeRepository.Base()
        val liveDataWrapper = FakeLiveDataWrapper.Base()
        val viewModel = MainViewModel(
            liveDataWrapper = liveDataWrapper,
            repository = repository
        )
        viewModel.load()
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowProgress, UiState.ShowData))
        repository.checkLoadCalledTimes(1)
    }
}

private interface FakeLiveDataWrapper : LiveDataWrapper {

    fun checkUpdateCalls(expected: List<UiState>)

    class Base : FakeLiveDataWrapper {

        private val actualCallsList = mutableListOf<UiState>()

        override fun checkUpdateCalls(expected: List<UiState>) {
            assertEquals(expected, actualCallsList)
        }

        override fun update(value: UiState) {
            actualCallsList.add(value)
        }

        override fun liveData(): LiveData<UiState> {
            throw IllegalStateException("not used in test")
        }
    }
}

private interface FakeRepository : Repository {

    fun checkLoadCalledTimes(times: Int)

    class Base : FakeRepository {

        var actualCalledTimes: Int = 0

        override fun checkLoadCalledTimes(times: Int) {
            assertEquals(times, actualCalledTimes)
        }

        override suspend fun load() {
            actualCalledTimes++
        }
    }
}