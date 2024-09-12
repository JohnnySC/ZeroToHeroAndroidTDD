package ru.easycode.zerotoheroandroidtdd

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.Serializable

/**
 * Please also take a look at ui test
 * @see [ru.easycode.zerotoheroandroidtdd.Task034UiTest]
 */
class CountTest {

    @Test
    fun increment() {
        var count: Count = Count.Base(value = 10, step = 3)
        assertEquals(true, count is Serializable)
        assertEquals("10", count.toString())

        count = count.increment()
        var actual = count
        var expected = Count.Base(value = 13, step = 3)
        assertEquals("13", count.toString())
        assertEquals(expected, actual)

        count = count.increment()
        actual = count
        expected = Count.Base(value = 16, step = 3)
        assertEquals("16", count.toString())
        assertEquals(expected, actual)
    }
}