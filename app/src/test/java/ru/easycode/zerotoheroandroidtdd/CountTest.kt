package ru.easycode.zerotoheroandroidtdd

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.Serializable

/**
 * Please also take a look at ui test
 * @see [ru.easycode.zerotoheroandroidtdd.Task035UiTest]
 */
class CountTest {

    @Test
    fun increment() {
        var count: Count = Count.Base(min = 0, max = 20, value = 15, step = 5)
        assertEquals(true, count is Serializable)
        assertEquals(false, count.isMax())
        assertEquals(false, count.isMin())
        assertEquals("15", count.toString())

        count = count.increment()
        val actual = count
        val expected: Count = Count.Base(min = 0, max = 20, value = 20, step = 5)
        assertEquals(true, count.isMax())
        assertEquals(false, count.isMin())
        assertEquals("20", count.toString())
        assertEquals(expected, actual)
    }

    @Test
    fun decrement() {
        var count: Count = Count.Base(min = 1, max = 13, value = 5, step = 4)
        assertEquals(true, count is Serializable)
        assertEquals(false, count.isMax())
        assertEquals(false, count.isMin())
        assertEquals("5", count.toString())

        count = count.decrement()
        val actual = count
        val expected: Count = Count.Base(min = 1, max = 13, value = 1, step = 4)
        assertEquals(false, count.isMax())
        assertEquals(true, count.isMin())
        assertEquals("1", count.toString())
        assertEquals(expected, actual)
    }
}