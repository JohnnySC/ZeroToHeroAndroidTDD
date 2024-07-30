package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.easycode.zerotoheroandroidtdd.data.SimpleResponse
import ru.easycode.zerotoheroandroidtdd.data.SimpleService

/**
 * Hint for success: use encoded true for url
 */
class ServiceTest {

    @Test
    fun test() = runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: SimpleService = retrofit.create(SimpleService::class.java)
        val actual =
            service.fetch(url = "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/task/018-clouddatasource/app/sampleresponse.json")
        val expected = SimpleResponse(text = "Hello World From Web!")
        assertEquals(expected, actual)
    }
}