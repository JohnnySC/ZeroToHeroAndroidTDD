package ru.easycode.zerotoheroandroidtdd

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    fun liveData(): LiveData<ArrayList<CharSequence>>
    fun update(text: String)

    class Base : LiveDataWrapper {

        private val liveData = MutableLiveData<ArrayList<CharSequence>>()
        init {
            Log.d("ml", "LiveDataWrapper.Base init")
            liveData.value = ArrayList()
        }

        override fun liveData(): LiveData<ArrayList<CharSequence>> = liveData
        override fun update(text: String) {
            val arrayList = liveData.value
            arrayList?.add(text)
            liveData.value = arrayList
        }
    }
}