package ru.easycode.zerotoheroandroidtdd

import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryTest {

    @Test
    fun test() {
        val now = FakeNow.Base(7777L)
        val dataSource = FakeDataSource.Base()
        val repository: Repository.Mutable = Repository.Base(dataSource = dataSource, now = now)

        dataSource.expectList(
            listOf(
                ItemCache(id = 0L, text = "first"),
                ItemCache(id = 1L, text = "second")
            )
        )

        val actual: List<String> = repository.list()
        val expected = listOf("first", "second")
        assertEquals(expected, actual)

        repository.add(value = "third")
        dataSource.checkList(
            listOf(
                ItemCache(id = 0L, text = "first"),
                ItemCache(id = 1L, text = "second"),
                ItemCache(id = 7778L, text = "third")
            )
        )
    }
}

private interface FakeNow : Now {

    class Base(private var value: Long) : FakeNow {

        override fun nowMillis(): Long {
            return ++value
        }
    }
}

private interface FakeDataSource : ItemsDao {

    fun checkList(expected: List<ItemCache>)

    fun expectList(list: List<ItemCache>)

    class Base : FakeDataSource {

        private val list = mutableListOf<ItemCache>()

        override fun checkList(expected: List<ItemCache>) {
            assertEquals(expected, list)
        }

        override fun expectList(list: List<ItemCache>) {
            this.list.addAll(list)
        }

        override fun list(): List<ItemCache> {
            return list
        }

        override fun add(item: ItemCache) {
            list.add(item)
        }
    }
}