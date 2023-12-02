package ru.easycode.zerotoheroandroidtdd.main

import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.Order

class MainViewModelTest {

    @Test
    fun test() {
        val order = Order()
        val navigation = FakeMutableNavigation(FakeNavigation.Base(order))
        val viewModel = MainViewModel(
            navigation = navigation
        )

        viewModel.init(firstRun = true)
        navigation.checkScreen(FoldersListScreen)
    }
}

private interface FakeMutableNavigation : FakeNavigation, Navigation.Read {

    class Base(private val fakeNavigation: FakeNavigation) : FakeMutableNavigation {

        override fun checkScreen(expected: Screen) {
            fakeNavigation.checkScreen(expected)
        }
    }
}