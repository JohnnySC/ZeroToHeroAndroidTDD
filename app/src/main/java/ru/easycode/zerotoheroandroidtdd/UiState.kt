package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import java.io.Serializable


interface UiState : Serializable {

    fun apply(textView: TextView, incrementButton: Button, decrementButton: Button)

    data class Base(
        private val text: String
    ) : UiState {
        override fun apply(textView: TextView, incrementButton: Button, decrementButton: Button) {
            textView.text = text
            decrementButton.isEnabled = true
            incrementButton.isEnabled = true
        }
    }

    data class Max(private val text: String) : UiState {
        override fun apply(textView: TextView, incrementButton: Button, decrementButton: Button) {
            textView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }
    }

    data class Min(private val text: String) : UiState {
        override fun apply(textView: TextView, incrementButton: Button, decrementButton: Button) {
            textView.text = text
            decrementButton.isEnabled = false
            incrementButton.isEnabled = true
        }

    }


}