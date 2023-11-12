package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals

interface FakeListLiveDataWrapper : ListLiveDataWrapper.All {

    fun checkCalledList(expected: List<CharSequence>)

    class Base : FakeListLiveDataWrapper {

        private val calledList = ArrayList<CharSequence>()

        override fun checkCalledList(expected: List<CharSequence>) {
            assertEquals(expected, calledList)
        }

        override fun add(source: CharSequence) {
            calledList.add(source)
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            bundleWrapper.save(calledList)
        }

        override fun liveData(): LiveData<List<CharSequence>> {
            throw IllegalStateException("not used in tests")
        }

        override fun update(value: List<CharSequence>) {
            calledList.addAll(value)
        }
    }
}