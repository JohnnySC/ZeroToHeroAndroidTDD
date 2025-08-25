package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressViewModelTest {

    @Test
    fun test() {
        val fakeRepository = FakeRepository()
        val fakeRunAsync = FakeRunAsync()
        val savedStateHandle = SavedStateHandle()
        var viewModel = ProgressViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = fakeRunAsync,
            repository = fakeRepository
        )

        val state: StateFlow<ProgressUi> = viewModel.state
        assertEquals(ProgressUi.Initial, state.value)

        viewModel.load()
        assertEquals(0, fakeRepository.loadCalledCount)
        assertEquals(ProgressUi.Loading, state.value)

        viewModel.loadInternal()
        assertEquals(1, fakeRepository.loadCalledCount)

        fakeRunAsync.returnResult()

        assertEquals(ProgressUi.Data(value = "fake success"), state.value)

        //process death happening here
        val newRepository = FakeRepository()
        viewModel = ProgressViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = FakeRunAsync(),
            repository = newRepository
        )
        assertEquals(ProgressUi.Data(value = "fake success"), viewModel.state.value)
        assertEquals(0, newRepository.loadCalledCount)
    }
}

private class FakeRepository : Repository {

    var loadCalledCount = 0

    override suspend fun load(): String {
        loadCalledCount++
        return "fake success"
    }
}

private class FakeRunAsync : RunAsync {

    lateinit var resultCached: Any
    var uiCached: (Any) -> Unit = {}

    override fun <T : Any> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    ) {
        runBlocking {
            resultCached = background.invoke()
            uiCached = ui as (Any) -> Unit
        }
    }

    fun returnResult() {
        uiCached.invoke(resultCached)
    }
}