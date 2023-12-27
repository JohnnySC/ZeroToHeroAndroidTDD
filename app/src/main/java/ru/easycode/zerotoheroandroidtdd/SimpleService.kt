package ru.easycode.zerotoheroandroidtdd

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface SimpleService {

    @GET("{fullUrl}")
    suspend fun fetch(@Path(value = "fullUrl", encoded = true) url: String): SimpleResponse


}

data class SimpleResponse(

    @SerializedName("text")
    val text: String
)