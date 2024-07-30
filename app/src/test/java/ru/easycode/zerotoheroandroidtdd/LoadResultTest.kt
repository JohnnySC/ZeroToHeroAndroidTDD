package ru.easycode.zerotoheroandroidtdd

import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.data.LoadResult
import ru.easycode.zerotoheroandroidtdd.data.SimpleResponse

class LoadResultTest {

    @Test
    fun test_success() {
        val result = LoadResult.Success(data = SimpleResponse(text = "some text here"))
        val liveDataWrapper = FakeLiveDataWrapper.Base()
        val liveDataWrapperUpdate: LiveDataWrapper.Update = liveDataWrapper
        result.show(updateLiveData = liveDataWrapperUpdate)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "some text here")))
    }

    @Test
    fun test_no_connection() {
        val result = LoadResult.Error(noConnection = true)
        val liveDataWrapper = FakeLiveDataWrapper.Base()
        val liveDataWrapperUpdate: LiveDataWrapper.Update = liveDataWrapper
        result.show(updateLiveData = liveDataWrapperUpdate)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "No internet connection")))
    }

    @Test
    fun test_other() {
        val result = LoadResult.Error(noConnection = false)
        val liveDataWrapper = FakeLiveDataWrapper.Base()
        val liveDataWrapperUpdate: LiveDataWrapper.Update = liveDataWrapper
        result.show(updateLiveData = liveDataWrapperUpdate)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "Something went wrong")))
    }
}