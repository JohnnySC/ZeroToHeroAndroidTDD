package ru.easycode.zerotoheroandroidtdd.create

import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.list.FakeListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.main.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.main.Screen

class CreateViewModelTest {

    private lateinit var viewModel: CreateViewModel
    private lateinit var addLiveDataWrapper: FakeListLiveDataWrapper
    private lateinit var navigation: FakeNavigation
    private lateinit var clearViewModel: FakeClearViewModel

    @Before
    fun setup() {
        addLiveDataWrapper = FakeListLiveDataWrapper.Base()
        val add: ListLiveDataWrapper.Add = addLiveDataWrapper

        navigation = FakeNavigation.Base()
        val navigationUpdate: Navigation.Update = navigation

        clearViewModel = FakeClearViewModel.Base()
        viewModel = CreateViewModel(
            addLiveDataWrapper = add,
            navigation = navigationUpdate,
            clearViewModel = clearViewModel
        )
    }

    @Test
    fun test_add() {
        viewModel.add(text = "exampleText")
        addLiveDataWrapper.checkCalledList(listOf("exampleText"))
        navigation.checkUpdateCalled(listOf(Screen.Pop))
        clearViewModel.checkClearCalled(CreateViewModel::class.java)
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()
        navigation.checkUpdateCalled(listOf(Screen.Pop))
        clearViewModel.checkClearCalled(CreateViewModel::class.java)
    }
}

private interface FakeClearViewModel : ClearViewModel {

    fun checkClearCalled(expected: Class<out ViewModel>)

    class Base : FakeClearViewModel {
        private lateinit var actual: Class<out ViewModel>

        override fun clear(viewModelClass: Class<out ViewModel>) {
            actual = viewModelClass
        }

        override fun checkClearCalled(expected: Class<out ViewModel>) {
            assertEquals(expected, actual)
        }
    }
}