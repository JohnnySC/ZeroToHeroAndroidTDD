package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData

interface FakeLiveDataWrapper {
    fun checkUpdateCalls(expected: List<UiState>)

    class Base : FakeLiveDataWrapper {

        private val actualCallsList = mutableListOf<UiState>()

        override fun checkUpdateCalls(expected: List<UiState>) {
        }

        fun update(value: UiState) {
            actualCallsList.add(value)
        }

        fun liveData(): LiveData<UiState> {
            throw IllegalStateException("not used in test")
        }
    }

}