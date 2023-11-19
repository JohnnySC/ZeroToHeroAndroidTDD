package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import java.io.Serializable


interface Count {

    fun increment(number: String): UiState
    class Base(val step: Int, val max: Int) : Count {
        private var result = 0

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
            if (max < 1)
                throw IllegalStateException("max should be positive, but was $max")
            if (max < step)
                throw IllegalStateException("max should be more than step")
        }

        override fun increment(number: String): UiState {
            val increment = number.toInt()
            result = increment + step
            val next = result + step
            return if (next <= max)
                UiState.Base(result.toString())
            else
                UiState.Max(result.toString())
        }

    }

}

interface UiState : Serializable {

    fun apply(textView: TextView, button: Button)
    data class Base(val text: String) : UiState {

        override fun apply(textView: TextView, button: Button) {
            textView.text = text
        }

    }

    data class Max(val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = false
        }
    }
}