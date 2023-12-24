package ru.easycode.zerotoheroandroidtdd

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

interface Repository {

    suspend fun load()

    class Base : Repository {

        override suspend fun load() {
            delay(3000)
        }
    }
}