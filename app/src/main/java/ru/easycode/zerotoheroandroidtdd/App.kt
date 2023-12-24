package ru.easycode.zerotoheroandroidtdd

import android.app.Application

class App : Application() {

    val viewModel =
        MainViewModel(repository = Repository.Base(), liveDataWrapper = LiveDataWrapper.Base())
}