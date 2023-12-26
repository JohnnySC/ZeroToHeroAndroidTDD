package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import android.util.Log

class App : Application() {

    init {
        Log.d("kia", "App init")
    }

    lateinit var viewModel: MainViewModel
    override fun onCreate() {
        super.onCreate()
        Log.d("kia", "App onCreate")
        viewModel= MainViewModel(
        Repository.Base(),
        LiveDataWrapper.Base())
    }
}