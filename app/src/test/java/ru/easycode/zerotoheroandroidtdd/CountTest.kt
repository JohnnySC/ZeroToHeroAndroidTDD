package ru.easycode.zerotoheroandroidtdd

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Please also check ui test
 * @see ru.easycode.zerotoheroandroidtdd.Task011Test
 */
class CountTest {

    @Test
    fun test_increment_step_5() {
        val count: Count = Count.Base(step = 5)
        var actual: String = count.increment(number = "0")
        var expected = "5"
        assertEquals(expected, actual)

        actual = count.increment(number = "10")
        expected = "15"
        assertEquals(expected, actual)
    }

    @Test
    fun test_increment_step_3() {
        val count: Count = Count.Base(step = 3)
        var actual: String = count.increment(number = "0")
        var expected = "3"
        assertEquals(expected, actual)

        actual = count.increment(number = "10")
        expected = "13"
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalStateException::class)
    fun test_zero() {
        Count.Base(step = 0)
    }

    @Test(expected = IllegalStateException::class)
    fun test_negative() {
        Count.Base(step = -1)
    }

    @Test
    fun test_negative_minus_2() {
        try {
            Count.Base(step = -2)
        } catch (e: Exception) {
            assertEquals("step should be positive, but was -2", e.message)
        }
    }
}