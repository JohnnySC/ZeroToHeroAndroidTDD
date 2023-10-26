package ru.easycode.zerotoheroandroidtdd

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Please also check ui test
 * @see ru.easycode.zerotoheroandroidtdd.Task012Test
 */
class CountTest {

    @Test
    fun test_increment_step_5() {
        val count: Count = Count.Base(step = 5, max = 10)

        var actual: UiState = count.increment(number = "0")
        var expected: UiState = UiState.Base(text = "5")
        assertEquals(expected, actual)

        actual = count.increment(number = "5")
        expected = UiState.Max(text = "10")
        assertEquals(expected, actual)
    }

    @Test
    fun test_increment_step_3() {
        val count: Count = Count.Base(step = 3, max = 7)

        var actual: UiState = count.increment(number = "0")
        var expected: UiState = UiState.Base(text = "3")
        assertEquals(expected, actual)

        actual = count.increment(number = "3")
        expected = UiState.Max(text = "6")
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalStateException::class)
    fun test_step_zero() {
        Count.Base(step = 0, max = 11)
    }

    @Test(expected = IllegalStateException::class)
    fun test_step_negative() {
        Count.Base(step = -1, max = 11)
    }

    @Test
    fun test_step_negative_message() {
        try {
            Count.Base(step = -2, max = 11)
        } catch (e: Exception) {
            assertEquals("step should be positive, but was -2", e.message)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_zero_max() {
        Count.Base(step = 3, max = 0)
    }

    @Test(expected = IllegalStateException::class)
    fun test_negative_max() {
        Count.Base(step = 3, max = -1)
    }

    @Test
    fun test_negative_max_message() {
        try {
            Count.Base(step = 5, max = -2)
        } catch (e: Exception) {
            assertEquals("max should be positive, but was -2", e.message)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_max_less_than_step() {
        Count.Base(step = 7, max = 6)
    }

    @Test
    fun test_max_less_than_step_message() {
        try {
            Count.Base(step = 5, max = 4)
        } catch (e: Exception) {
            assertEquals("max should be more than step", e.message)
        }
    }
}