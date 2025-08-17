package ru.easycode.zerotoheroandroidtdd

class MainViewModel(
    private val listLiveDataWrapper: ListLiveDataWrapper
) {

    fun liveData() = listLiveDataWrapper.liveData()

    fun add(text: String) {
        listLiveDataWrapper.add(text)
    }

    fun save(bundle: BundleWrapper.Save) {
        listLiveDataWrapper.save(bundle)
    }

    fun restore(bundle: BundleWrapper.Restore) {
        listLiveDataWrapper.update(bundle.restore())
    }
}
