package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.data.LoadResult
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.data.SimpleResponse
import ru.easycode.zerotoheroandroidtdd.data.SimpleService
import java.net.UnknownHostException

class RepositoryTest {

    @Test
    fun test_success() = runBlocking {
        val service = FakeService.Base()
        service.expectSuccess()
        val repository = Repository.Base(service = service, url = "a")
        val actual = repository.load()
        val expected = LoadResult.Success(data = SimpleResponse(text = "A"))
        assertEquals(expected, actual)
    }

    @Test
    fun test_no_connection() = runBlocking {
        val service = FakeService.Base()
        service.expectException(UnknownHostException())

        val repository = Repository.Base(service = service, url = "a")
        val actual = repository.load()
        val expected = LoadResult.Error(noConnection = true)
        assertEquals(expected, actual)
    }

    @Test
    fun test_other_exception() = runBlocking {
        val service = FakeService.Base()
        service.expectException(IllegalStateException())

        val repository = Repository.Base(service = service, url = "a")
        val actual = repository.load()
        val expected = LoadResult.Error(noConnection = false)
        assertEquals(expected, actual)
    }
}

private interface FakeService : SimpleService {

    fun expectSuccess()

    fun expectException(exception: Exception)

    class Base : FakeService {

        private val map = mutableMapOf<String, SimpleResponse>()

        init {
            map["a"] = SimpleResponse(text = "A")
            map["b"] = SimpleResponse(text = "B")
        }

        private var expectSuccessResult: Boolean = false

        private lateinit var exceptionToThrow: Exception

        override fun expectSuccess() {
            expectSuccessResult = true
        }

        override fun expectException(exception: Exception) {
            exceptionToThrow = exception
        }

        override suspend fun fetch(url: String): SimpleResponse {
            if (expectSuccessResult)
                return map[url]!!
            else
                throw exceptionToThrow
        }
    }
}