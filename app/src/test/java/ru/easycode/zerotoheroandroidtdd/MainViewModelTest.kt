package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var listLiveDataWrapper: FakeListLiveDataWrapper

    @Before
    fun init() {
        listLiveDataWrapper = FakeListLiveDataWrapper.Base()
        viewModel = MainViewModel(listLiveDataWrapper = listLiveDataWrapper)
    }

    @Test
    fun test() {
        viewModel.add(text = "first")
        listLiveDataWrapper.checkListSame(listOf("first"))

        viewModel.add(text = "second")
        listLiveDataWrapper.checkListSame(listOf("first", "second"))

        val bundleWrapper: BundleWrapper.Mutable = FakeBundleWrapper()
        val bundleWrapperSave: BundleWrapper.Save = bundleWrapper
        val bundleWrapperRestore: BundleWrapper.Restore = bundleWrapper

        viewModel.save(bundle = bundleWrapperSave)

        init()

        viewModel.restore(bundle = bundleWrapperRestore)
        listLiveDataWrapper.checkListSame(listOf("first", "second"))
    }
}

private interface FakeListLiveDataWrapper : ListLiveDataWrapper {

    fun checkListSame(expected: List<CharSequence>)

    class Base : FakeListLiveDataWrapper {

        private val list = ArrayList<CharSequence>()

        override fun checkListSame(expected: List<CharSequence>) {
            assertEquals(expected, list)
        }

        override fun liveData(): LiveData<List<CharSequence>> {
            throw IllegalStateException("not used here")
        }

        override fun add(new: CharSequence) {
            list.add(new)
        }

        override fun save(bundle: BundleWrapper.Save) {
            bundle.save(list)
        }

        override fun update(list: List<CharSequence>) {
            this.list.addAll(list)
        }
    }
}

class FakeBundleWrapper : BundleWrapper.Mutable {

    private val cache = ArrayList<CharSequence>()

    override fun save(list: ArrayList<CharSequence>) {
        cache.addAll(list)
    }

    override fun restore(): List<CharSequence> {
        return cache
    }
}