package ru.easycode.zerotoheroandroidtdd

import java.net.UnknownHostException

interface Repository {

    suspend fun load(): LoadResult

    class Base(
        private val service: SimpleService,
        private val url: String
    ) : Repository {
        override suspend fun load(): LoadResult = try {
            LoadResult.Success(service.fetch(url))
        } catch (e: Exception) {
            LoadResult.Error(e is UnknownHostException)
        }
    }
}

interface LoadResult {

    fun show(updateLiveData: LiveDataWrapper.Update)
    data class Success(private val data: SimpleResponse) : LoadResult {
        override fun show(updateLiveData: LiveDataWrapper.Update) {
            updateLiveData.update(UiState.ShowData(data.text))
        }
    }

    data class Error(private val noConnection: Boolean) : LoadResult {

        private val textException = if (noConnection) "No internet connection"
        else "Something went wrong"

        override fun show(updateLiveData: LiveDataWrapper.Update) =
            updateLiveData.update(UiState.ShowData(textException))
    }
}