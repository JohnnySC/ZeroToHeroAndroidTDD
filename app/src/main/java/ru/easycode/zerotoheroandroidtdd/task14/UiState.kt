package ru.easycode.zerotoheroandroidtdd.task14

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

interface UiState {

    fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button)

    object Initial : UiState {
        override fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            progressBar.visibility = View.GONE
            textView.visibility = View.GONE
            actionButton.visibility = View.VISIBLE
        }
    }

    object Loading : UiState {
        override fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            progressBar.visibility = View.VISIBLE
            actionButton.isEnabled = false
        }
    }

    object Base : UiState {
        override fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            actionButton.isEnabled = true
        }

    }
}