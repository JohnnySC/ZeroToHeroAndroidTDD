package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.data.Repository

class MainViewModel(
    private val liveDataWrapper: LiveDataWrapper,
    private val repository: Repository
) {

    private val viewModelScoupe = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        liveDataWrapper.update(UiState.Initial)
    }

    fun load() {
        liveDataWrapper.update(UiState.ShowProgress)
        viewModelScoupe.launch {
            val response = repository.load()
            withContext(Dispatchers.Main) {
                liveDataWrapper.update(UiState.ShowData(response.text))
            }
        }
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        liveDataWrapper.update(bundleWrapper.restore())
    }

    fun observe(lifecycleOwner: LifecycleOwner, block: (state: UiState) -> Unit) {
        liveDataWrapper.liveData().observe(lifecycleOwner) {
            liveDataWrapper.liveData().value?.let { value -> block.invoke(value) }
        }
    }
}