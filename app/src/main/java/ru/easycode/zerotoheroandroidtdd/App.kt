package ru.easycode.zerotoheroandroidtdd

import android.app.Application

class App : Application() {

    lateinit var mainViewModel: MainViewModel
    override fun onCreate() {
        super.onCreate()
        val liveDataWrapper = LiveDataWrapper.Base()
        mainViewModel = MainViewModel(liveDataWrapper)
    }
}