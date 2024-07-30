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
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.data.SimpleResponse

/**
 * Please also check out the ui test
 * @see ru.easycode.zerotoheroandroidtdd.Task018Test
 *
 * And other unit tests
 * @see RepositoryTest
 * @see ServiceTest
 */
class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        initialize()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private lateinit var repository: FakeRepository
    private lateinit var liveDataWrapper: FakeLiveDataWrapper
    private lateinit var viewModel: MainViewModel

    fun initialize() {
        repository = FakeRepository.Base()
        liveDataWrapper = FakeLiveDataWrapper.Base()
        viewModel = MainViewModel(
            liveDataWrapper = liveDataWrapper,
            repository = repository
        )
    }

    @Test
    fun test() {
        repository.expectResponse(SimpleResponse(text = "testingText"))

        viewModel.load()
        liveDataWrapper.checkUpdateCalls(
            listOf(
                UiState.ShowProgress,
                UiState.ShowData(text = "testingText")
            )
        )
        repository.checkLoadCalledTimes(1)

        val bundleWrapper: BundleWrapper.Mutable = FakeBundleWrapper.Base()
        val bundleWrapperSave: BundleWrapper.Save = bundleWrapper
        val bundleWrapperRestore: BundleWrapper.Restore = bundleWrapper

        viewModel.save(bundleWrapper = bundleWrapperSave)

        initialize()

        viewModel.restore(bundleWrapper = bundleWrapperRestore)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "testingText")))
        repository.checkLoadCalledTimes(0)
    }
}

private interface FakeBundleWrapper : BundleWrapper.Mutable {

    class Base : FakeBundleWrapper {

        private var uiState: UiState? = null

        override fun save(uiState: UiState) {
            this.uiState = uiState
        }

        override fun restore(): UiState = uiState!!
    }
}

private interface FakeLiveDataWrapper : LiveDataWrapper {

    fun checkUpdateCalls(expected: List<UiState>)

    class Base : FakeLiveDataWrapper {

        private val actualCallsList = mutableListOf<UiState>()

        override fun checkUpdateCalls(expected: List<UiState>) {
            assertEquals(expected, actualCallsList)
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            bundleWrapper.save(actualCallsList.last())
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

    fun expectResponse(simpleResponse: SimpleResponse)

    fun checkLoadCalledTimes(times: Int)

    class Base : FakeRepository {

        private lateinit var response: SimpleResponse

        override fun expectResponse(simpleResponse: SimpleResponse) {
            response = simpleResponse
        }

        private var actualCalledTimes: Int = 0

        override fun checkLoadCalledTimes(times: Int) {
            assertEquals(times, actualCalledTimes)
        }

        override suspend fun load(): SimpleResponse {
            actualCalledTimes++
            return response
        }
    }
}