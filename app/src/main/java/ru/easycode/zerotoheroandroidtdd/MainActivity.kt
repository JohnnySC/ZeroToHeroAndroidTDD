package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var btn: Button
    private var state: UiState = UiState.Base("0")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.countTextView)
        btn = findViewById(R.id.incrementButton)
        val count = Count.Base(2, 4)

        btn.setOnClickListener {
            state = count.increment(textView.text.toString())
            state.apply(textView,btn)



        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java) as UiState
        } else {
            savedInstanceState.getSerializable(KEY) as UiState
        }
        state.apply(textView, btn)
    }

    companion object {
        const val KEY = "key"
    }
}
