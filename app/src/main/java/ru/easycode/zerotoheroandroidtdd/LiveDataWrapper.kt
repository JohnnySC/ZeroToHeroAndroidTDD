package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData

interface LiveDataWrapper {

    interface Update : LiveDataWrapper {
        fun update(value: UiState)
    }

    interface Save : LiveDataWrapper {
        fun save(bundleWrapper: BundleWrapper.Save)
    }

    interface Mutable : Update, Save

    fun liveData(): LiveData<UiState>

    class Base : Mutable {

        private val liveData = SingleLiveEvent<UiState>()

        init {
            liveData.value = UiState.Initial
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let { bundleWrapper.save(it) }
        }

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData(): LiveData<UiState> = liveData
    }
}