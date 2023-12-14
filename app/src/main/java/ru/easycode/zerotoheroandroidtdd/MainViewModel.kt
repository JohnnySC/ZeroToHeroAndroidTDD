package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val liveDataWrapper: LiveDataWrapper) : ViewModel() {
    fun liveData(): LiveData<ArrayList<CharSequence>> = liveDataWrapper.liveData()
    fun addText(text: String) {
        liveDataWrapper.update(text)
    }

}