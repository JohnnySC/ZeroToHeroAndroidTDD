package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.data.LoadResult
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.data.SimpleResponse

/**
 * Please also check out the ui test
 * @see ru.easycode.zerotoheroandroidtdd.Task019Test
 *
 * And other unit tests
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
        repository.expectResult(LoadResult.Success(SimpleResponse(text = "testingText")))

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

private interface FakeRepository : Repository {

    fun expectResult(result: LoadResult)

    fun checkLoadCalledTimes(times: Int)

    class Base : FakeRepository {

        private lateinit var result: LoadResult

        override fun expectResult(result: LoadResult) {
            this.result = result
        }

        private var actualCalledTimes: Int = 0

        override fun checkLoadCalledTimes(times: Int) {
            assertEquals(times, actualCalledTimes)
        }

        override suspend fun load(): LoadResult {
            actualCalledTimes++
            return result
        }
    }
}