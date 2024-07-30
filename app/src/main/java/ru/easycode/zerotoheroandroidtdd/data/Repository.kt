package ru.easycode.zerotoheroandroidtdd.data

import java.net.UnknownHostException


interface Repository {

    suspend fun load(): LoadResult

    class Base(
        private val service: SimpleService,
        private val url: String
    ) : Repository {

        override suspend fun load(): LoadResult = try {
            LoadResult.Success(service.fetch(url))
        } catch (e: UnknownHostException) {
            LoadResult.Error(true)
        } catch (e: Exception) {
            LoadResult.Error(false)
        }
    }
}