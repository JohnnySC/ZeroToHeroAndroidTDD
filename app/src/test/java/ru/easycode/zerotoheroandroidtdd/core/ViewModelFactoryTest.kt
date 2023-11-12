package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelFactoryTest {

    private lateinit var provideViewModel: FakeProvideViewModel
    private lateinit var factory: ViewModelFactory

    @Before
    fun setup() {
        provideViewModel = FakeProvideViewModel.Base()
        factory = ViewModelFactory.Base(provideViewModel = provideViewModel)
    }

    @Test
    fun test_cached_same() {
        factory.viewModel(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(listOf(FakeViewModelOne::class.java))

        factory.viewModel(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(listOf(FakeViewModelOne::class.java))
    }

    @Test
    fun test_called_other() {
        factory.viewModel(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(listOf(FakeViewModelOne::class.java))

        factory.viewModel(viewModelClass = FakeViewModelTwo::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java
            )
        )
    }

    @Test
    fun test_clear_first() {
        factory.viewModel(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(listOf(FakeViewModelOne::class.java))

        factory.viewModel(viewModelClass = FakeViewModelTwo::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java
            )
        )

        factory.clear(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java
            )
        )

        factory.viewModel(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java,
                FakeViewModelOne::class.java,
            )
        )
    }

    @Test
    fun test_clear_second() {
        factory.viewModel(viewModelClass = FakeViewModelOne::class.java)
        provideViewModel.checkCalled(listOf(FakeViewModelOne::class.java))

        factory.viewModel(viewModelClass = FakeViewModelTwo::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java
            )
        )

        factory.clear(viewModelClass = FakeViewModelTwo::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java
            )
        )

        factory.viewModel(viewModelClass = FakeViewModelTwo::class.java)
        provideViewModel.checkCalled(
            listOf(
                FakeViewModelOne::class.java,
                FakeViewModelTwo::class.java,
                FakeViewModelTwo::class.java,
            )
        )
    }
}

private interface FakeProvideViewModel : ProvideViewModel {

    fun checkCalled(expected: List<Class<out ViewModel>>)

    class Base : FakeProvideViewModel {

        private val list = mutableListOf<Class<out ViewModel>>()

        override fun checkCalled(expected: List<Class<out ViewModel>>) {
            assertEquals(expected, list)
        }

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            list.add(viewModelClass)
            return viewModelClass.getDeclaredConstructor().newInstance()
        }
    }
}

private class FakeViewModelOne : ViewModel()

private class FakeViewModelTwo : ViewModel()