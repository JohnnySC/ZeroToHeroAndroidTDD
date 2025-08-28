package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

@RunWith(AndroidJUnit4::class)
class Task041UiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeRunAsync: FakeRunAsync
    private lateinit var fakeConnection: FakeConnection
    private lateinit var fakeRepository: FakeRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        fakeRunAsync = FakeRunAsync()
        fakeConnection = FakeConnection()
        fakeRepository = FakeRepository()
        viewModel = MainViewModel(
            runAsync = fakeRunAsync,
            repository = fakeRepository,
            connection = fakeConnection
        )
    }

    @Test
    fun test(): Unit = with(composeTestRule) {
        setContent {
            ZeroToHeroAndroidTDDTheme {
                MainScreen(viewModel = viewModel)
            }
        }
        val noInternet = onNodeWithTag("noInternetConnection")
        val loadButton = onNodeWithTag("loadButton")
        val progress = onNodeWithTag("progress")
        val result = onNodeWithTag("result")

        loadButton.assertIsNotEnabled()
        result.assertDoesNotExist()
        progress.assertDoesNotExist()
        noInternet.assertIsDisplayed()

        fakeConnection.changeConnected(true)

        loadButton.assertIsEnabled()
        result.assertDoesNotExist()
        progress.assertDoesNotExist()
        noInternet.assertDoesNotExist()

        loadButton.performClick()

        loadButton.assertDoesNotExist()
        progress.assertIsDisplayed()
        result.assertDoesNotExist()
        noInternet.assertDoesNotExist()

        fakeRunAsync.returnResult()

        loadButton.assertDoesNotExist()
        progress.assertDoesNotExist()
        result.assertTextEquals("Fake Data")
        noInternet.assertDoesNotExist()
    }
}

private class FakeConnection : MonitorConnection {
    private val mutableStateFlow = MutableStateFlow(false)

    override fun connectedFlow(): StateFlow<Boolean> = mutableStateFlow

    fun changeConnected(connected: Boolean) {
        mutableStateFlow.value = connected
    }
}

@Suppress("UNCHECKED_CAST")
private class FakeRunAsync : RunAsync {

    private lateinit var resultCached: Any
    private lateinit var uiCached: (Any) -> Unit

    override fun <T : Any> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    ) {
        runBlocking {
            val result: T = background.invoke()
            resultCached = result
            uiCached = ui as (Any) -> Unit
        }
    }

    fun returnResult() {
        uiCached.invoke(resultCached)
    }
}

private class FakeRepository : Repository {

    override suspend fun load(): String = "Fake Data"
}