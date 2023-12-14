package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.MutableLiveData

class App : Application() {

    lateinit var liveData: MutableLiveData<ArrayList<CharSequence>>
    override fun onCreate() {
        super.onCreate()
        liveData = MutableLiveData()
        liveData.value = ArrayList()
    }
}