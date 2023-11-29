package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state: State = State.Initial

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var linearLayout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.titleTextView)
        button = findViewById(R.id.removeButton)
        linearLayout = findViewById(R.id.rootLayout)

        button.setOnClickListener {
            state = State.Removed
            state.apply(linearLayout, textView, button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    @Suppress("DEPRECATION")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = (savedInstanceState.getSerializable(KEY) ?: State.Initial) as State
        state.apply(linearLayout, textView, button)
    }

    companion object {
        private const val KEY = "key"
    }
}

interface State : Serializable {

    fun apply(linearLayout: LinearLayout, textView: TextView, button: Button)

    object Initial : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView, button: Button) = Unit
    }

    object Removed : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView, button: Button) {
            linearLayout.removeView(textView)
            button.isEnabled = false
        }
    }
}
