package ru.easycode.zerotoheroandroidtdd

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
            repository.load()
            liveDataWrapper.update(UiState.ShowData)
        }
    }
    override fun liveData() = liveDataWrapper.liveData()

}