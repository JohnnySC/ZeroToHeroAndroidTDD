package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    interface Update {
        fun update(value: UiState)
    }

    interface Save {
        fun save(bundleWrapper: BundleWrapper.Save)
    }

    interface Observe {
        fun liveData(): LiveData<UiState>
    }

    interface Mutable: Update, Save, Observe


    class Base(
        private val liveData: MutableLiveData<UiState> = SingleLiveEvent()
    ) : Mutable {

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let {
                bundleWrapper.save(it)
            }
        }
        override fun liveData(): LiveData<UiState> {
            return liveData
        }
    }
}