package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals

interface FakeListLiveDataWrapper : ListLiveDataWrapper.All {

    companion object {
        const val LIVE_DATA_DELETE = "ListLiveDataWrapper#delete"
    }

    fun checkUpdateCallList(expected: List<ItemUi>)

    class Base(private val order: Order = Order()) : FakeListLiveDataWrapper {

        private val actual = mutableListOf<ItemUi>()

        override fun checkUpdateCallList(expected: List<ItemUi>) {
            assertEquals(expected, actual)
        }

        override fun update(value: List<ItemUi>) {
            actual.clear()
            actual.addAll(value)
        }

        override fun liveData(): LiveData<List<ItemUi>> {
            throw IllegalStateException("not used")
        }

        override fun add(value: ItemUi) {
            actual.add(value)
        }

        override fun delete(item: ItemUi) {
            order.add(LIVE_DATA_DELETE)
            actual.remove(item)
        }
    }
}