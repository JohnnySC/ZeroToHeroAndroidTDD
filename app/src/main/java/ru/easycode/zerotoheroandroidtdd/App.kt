package ru.easycode.zerotoheroandroidtdd

import android.app.Application

class App : Application() {

    lateinit var liveData: LiveDataWrapper
    override fun onCreate() {
        super.onCreate()
        liveData = LiveDataWrapper.Base()
    }
}