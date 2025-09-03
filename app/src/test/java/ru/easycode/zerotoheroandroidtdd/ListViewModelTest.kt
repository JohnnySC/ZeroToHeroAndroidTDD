package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Also check Task042UiTest
 */
class ListViewModelTest {

    @Test
    fun test() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = ListViewModel(
            savedStateHandle = savedStateHandle
        )

        val state: StateFlow<List<String>> = viewModel.state
        assertEquals(emptyList<String>(), state.value)

        viewModel.add(text = "text number one")
        assertEquals(listOf("text number one"), state.value)

        viewModel.add(text = "text number two")
        assertEquals(
            listOf(
                "text number two",
                "text number one",
            ), state.value
        )

        //process recreate happen here
        val newViewModel = ListViewModel(
            savedStateHandle = savedStateHandle
        )
        assertEquals(
            listOf(
                "text number two",
                "text number one",
            ), newViewModel.state.value
        )
    }
}