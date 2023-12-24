package ru.easycode.zerotoheroandroidtdd
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

interface UiState {

    fun apply(button: Button, progressBar: ProgressBar, textView: TextView)

    object ShowProgress : UiState {
        override fun apply(button: Button, progressBar: ProgressBar, textView: TextView) {
            button.isEnabled = false
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.GONE
        }
    }
    object ShowData : UiState {
        override fun apply(button: Button, progressBar: ProgressBar, textView: TextView) {
            button.isEnabled = true
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
    }
}
}