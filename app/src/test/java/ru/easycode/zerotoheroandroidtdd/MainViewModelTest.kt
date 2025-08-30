package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * The same as in Task040 ViewModelTest
 */
class MainViewModelTest {

    private lateinit var fakeRepository: FakeRepository
    private lateinit var fakeRunAsync: FakeRunAsync
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        fakeRunAsync = FakeRunAsync()
        savedStateHandle = SavedStateHandle()
        viewModel = MainViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = fakeRunAsync,
            repository = fakeRepository,
            connection = FakeConnection()
        )
    }

    @Test
    fun disconnected_then_connected() {
        val state: StateFlow<ProgressUi> = viewModel.stateFlow
        assertEquals(ProgressUi.Empty, state.value)

        viewModel.initial(connected = false)
        assertEquals(ProgressUi.Disconnected, state.value)

        viewModel.update(alreadyConnected = false, connected = true)
        assertEquals(ProgressUi.Connected, state.value)

        viewModel.load()
        assertEquals(0, fakeRepository.loadCalledCount)
        assertEquals(ProgressUi.Loading, state.value)

        viewModel.loadInternal()
        assertEquals(1, fakeRepository.loadCalledCount)

        fakeRunAsync.returnResult()

        assertEquals(ProgressUi.Data(value = "fake success"), state.value)

        //process death happening here
        val newRepository = FakeRepository()
        viewModel = MainViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = FakeRunAsync(),
            repository = newRepository,
            connection = FakeConnection()
        )
        assertEquals(ProgressUi.Data(value = "fake success"), viewModel.stateFlow.value)
        assertEquals(0, newRepository.loadCalledCount)
    }

    @Test
    fun disconnected_then_connected_duplicate() {
        val state: StateFlow<ProgressUi> = viewModel.stateFlow
        assertEquals(ProgressUi.Empty, state.value)

        fakeRunAsync.returnFlowResult(connected = false)
        assertEquals(ProgressUi.Disconnected, state.value)

        fakeRunAsync.returnFlowResult(connected = true)
        assertEquals(ProgressUi.Connected, state.value)

        viewModel.load()
        assertEquals(0, fakeRepository.loadCalledCount)
        assertEquals(ProgressUi.Loading, state.value)

        viewModel.loadInternal()
        assertEquals(1, fakeRepository.loadCalledCount)

        fakeRunAsync.returnResult()

        assertEquals(ProgressUi.Data(value = "fake success"), state.value)

        //process death happening here
        val newRepository = FakeRepository()
        viewModel = MainViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = FakeRunAsync(),
            repository = newRepository,
            connection = FakeConnection()
        )
        assertEquals(ProgressUi.Data(value = "fake success"), viewModel.stateFlow.value)
        assertEquals(0, newRepository.loadCalledCount)
    }

    @Test
    fun connected_then_disconnected_then_connected() {
        val state: StateFlow<ProgressUi> = viewModel.stateFlow
        assertEquals(ProgressUi.Empty, state.value)

        viewModel.initial(connected = true)
        assertEquals(ProgressUi.Connected, state.value)

        viewModel.update(alreadyConnected = true, connected = false)
        assertEquals(ProgressUi.Disconnected, state.value)

        viewModel.update(alreadyConnected = false, connected = true)
        assertEquals(ProgressUi.Connected, state.value)

        viewModel.load()
        assertEquals(0, fakeRepository.loadCalledCount)
        assertEquals(ProgressUi.Loading, state.value)

        viewModel.loadInternal()
        assertEquals(1, fakeRepository.loadCalledCount)

        fakeRunAsync.returnResult()

        assertEquals(ProgressUi.Data(value = "fake success"), state.value)

        //process death happening here
        val newRepository = FakeRepository()
        viewModel = MainViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = FakeRunAsync(),
            repository = newRepository,
            connection = FakeConnection()
        )
        assertEquals(ProgressUi.Data(value = "fake success"), viewModel.stateFlow.value)
        assertEquals(0, newRepository.loadCalledCount)
    }

    @Test
    fun connected_then_disconnected_then_connected_duplicate() {
        val state: StateFlow<ProgressUi> = viewModel.stateFlow
        assertEquals(ProgressUi.Empty, state.value)

        fakeRunAsync.returnFlowResult(connected = true)
        assertEquals(ProgressUi.Connected, state.value)

        fakeRunAsync.returnFlowResult(connected = false)
        assertEquals(ProgressUi.Disconnected, state.value)

        fakeRunAsync.returnFlowResult(connected = true)
        assertEquals(ProgressUi.Connected, state.value)

        viewModel.load()
        assertEquals(0, fakeRepository.loadCalledCount)
        assertEquals(ProgressUi.Loading, state.value)

        viewModel.loadInternal()
        assertEquals(1, fakeRepository.loadCalledCount)

        fakeRunAsync.returnResult()

        assertEquals(ProgressUi.Data(value = "fake success"), state.value)

        //process death happening here
        val newRepository = FakeRepository()
        viewModel = MainViewModel(
            savedStateHandle = savedStateHandle,
            runAsync = FakeRunAsync(),
            repository = newRepository,
            connection = FakeConnection()
        )
        assertEquals(ProgressUi.Data(value = "fake success"), viewModel.stateFlow.value)
        assertEquals(0, newRepository.loadCalledCount)
    }

    @Test
    fun connectivity_changes_no_effect() {
        viewModel.load()
        assertEquals(ProgressUi.Loading, viewModel.stateFlow.value)

        viewModel.update(alreadyConnected = true, connected = true)
        assertEquals(ProgressUi.Loading, viewModel.stateFlow.value)

        viewModel.update(alreadyConnected = false, connected = false)
        assertEquals(ProgressUi.Loading, viewModel.stateFlow.value)
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

    private lateinit var onEachCached: (Any) -> Unit

    override fun <T : Any> runFlow(
        scope: CoroutineScope,
        flow: Flow<T>,
        onEach: (T) -> Unit
    ) {
        onEachCached = onEach as (Any) -> Unit
    }

    fun returnFlowResult(connected: Boolean) {
        onEachCached.invoke(connected)
    }

    fun returnResult() {
        uiCached.invoke(resultCached)
    }
}

private class FakeConnection : MonitorConnection {
    private val mutableStateFlow = MutableStateFlow(false)

    override fun connectedFlow(): StateFlow<Boolean> = mutableStateFlow

    fun changeConnected(connected: Boolean) {
        mutableStateFlow.value = connected
    }
}