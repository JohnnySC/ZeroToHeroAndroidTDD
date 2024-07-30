package ru.easycode.zerotoheroandroidtdd.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpleService {

    @GET("{fullUrl}")
    suspend fun fetch(@Path(value = "fullUrl", encoded = true) url:String) : SimpleResponse

}


data class SimpleResponse(
    @SerializedName("text")
    val text: String
)