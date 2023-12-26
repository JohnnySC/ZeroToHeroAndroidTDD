package ru.easycode.zerotoheroandroidtdd

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
    private val liveDataWrapper: LiveDataWrapper
) : ViewModel(), ProvideLiveData {

    private val viewModelScope = CoroutineScope(
        SupervisorJob(Job()) +
                Dispatchers.Main.immediate
    )
    fun load() {
        liveDataWrapper.update(UiState.ShowProgress)
        viewModelScope.launch {
            val response = repository.load()
            liveDataWrapper.update(UiState.ShowData(response.text))
        }
    }
    override fun liveData() = liveDataWrapper.liveData()

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }
    fun restore(bundleWrapper: BundleWrapper.Restore) {
        val uiState = bundleWrapper.restore()
        liveDataWrapper.update(uiState)
    }
}