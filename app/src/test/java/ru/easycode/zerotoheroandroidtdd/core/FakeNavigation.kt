package ru.easycode.zerotoheroandroidtdd.core

import org.junit.Assert.assertEquals
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.main.Screen

interface FakeNavigation : Navigation.Mutable {

    interface Update : Navigation.Update {
        fun checkScreen(expected: Screen)
    }

    interface Mutable : Update, Navigation.Mutable

    companion object {
        const val NAVIGATE = "Navigation#navigate"
    }

    class Base(private val order: Order) : Mutable {

        private lateinit var actual: Screen

        override fun update(screen: Screen) {
            actual = screen
            order.add(NAVIGATE)
        }

        override fun checkScreen(expected: Screen) {
            assertEquals(expected, actual)
        }
    }
}