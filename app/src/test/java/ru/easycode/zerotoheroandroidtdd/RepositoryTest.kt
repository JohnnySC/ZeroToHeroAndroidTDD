package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryTest {

    @Test
    fun test() = runBlocking {
        val service = FakeService.Base()
        val repository = Repository.Base(service = service, url = "a")
        val actual = repository.load()
        val expected = SimpleResponse(text = "A")
        assertEquals(expected, actual)
    }
}

private interface FakeService : SimpleService {

    class Base : FakeService {

        private val map = mutableMapOf<String, SimpleResponse>()

        init {
            map["a"] = SimpleResponse(text = "A")
            map["b"] = SimpleResponse(text = "B")
        }

        override suspend fun fetch(url: String): SimpleResponse {
            return map[url]!!
        }
    }
}