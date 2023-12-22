package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var incrementButton: Button
    private lateinit var decrementButton: Button
    private val count = Count.Base(2, 4, 0)
    private lateinit var uiState: UiState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incrementButton = findViewById(R.id.incrementButton)
        decrementButton = findViewById(R.id.decrementButton)
        textView = findViewById(R.id.countTextView)

        if (savedInstanceState == null) {
            uiState = count.initial(textView.text.toString())
            uiState.apply(textView, incrementButton, decrementButton)
        }

        incrementButton.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(textView, incrementButton, decrementButton)
        }

        decrementButton.setOnClickListener {
            uiState = count.decrement(textView.text.toString())
            uiState.apply(textView, incrementButton, decrementButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            uiState = savedInstanceState.getSerializable(KEY, UiState::class.java)!!
            uiState.apply(textView, incrementButton, decrementButton)
        }
    }

    companion object {
        private const val KEY = "initialKey"
    }
}