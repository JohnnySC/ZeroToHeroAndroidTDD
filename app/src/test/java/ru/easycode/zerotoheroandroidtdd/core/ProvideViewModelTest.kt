package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import org.junit.Test

class ProvideViewModelTest {

    @Test
    fun test() {
        val order = Order()
        val provide = FakeProvide.Base(order)
        val factory = ProvideViewModel.Factory(provide)

        factory.viewModel(FakeViewModelOne::class.java)
        factory.viewModel(FakeViewModelTwo::class.java)
        factory.viewModel(FakeViewModelOne::class.java)
        factory.viewModel(FakeViewModelTwo::class.java)
        factory.viewModel(FakeViewModelThree::class.java)
        order.check(listOf("FakeViewModelOne", "FakeViewModelTwo", "FakeViewModelThree"))
        factory.clear(FakeViewModelOne::class.java, FakeViewModelTwo::class.java)

        factory.viewModel(FakeViewModelOne::class.java)
        factory.viewModel(FakeViewModelTwo::class.java)
        factory.viewModel(FakeViewModelThree::class.java)
        order.check(
            listOf(
                "FakeViewModelOne",
                "FakeViewModelTwo",
                "FakeViewModelThree",
                "FakeViewModelOne",
                "FakeViewModelTwo"
            )
        )
    }
}

private interface FakeProvide : ProvideViewModel {

    class Base(
        private val order: Order
    ) : FakeProvide {

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            order.add(clasz.simpleName)
            return clasz.getDeclaredConstructor().newInstance()
        }
    }
}

private class FakeViewModelOne : ViewModel()
private class FakeViewModelTwo : ViewModel()
private class FakeViewModelThree : ViewModel()