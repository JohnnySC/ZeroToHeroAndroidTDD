package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.Serializable

interface UiState : Serializable {

    fun apply(progressBar: ProgressBar, textView: TextView, button: Button)

    object Initial : UiState {
        override fun apply(progressBar: ProgressBar, textView: TextView, button: Button) {
            progressBar.visibility = View.GONE
            textView.visibility = View.GONE
            button.isEnabled = true
        }
    }

    object ShowProgress : UiState {
        override fun apply(progressBar: ProgressBar, textView: TextView, button: Button) {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.GONE
            button.isEnabled = false
        }
    }

    data class ShowData(private val text: String) : UiState {
        override fun apply(progressBar: ProgressBar, textView: TextView, button: Button) {
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            textView.text = text
            button.isEnabled = true
        }
    }
}
