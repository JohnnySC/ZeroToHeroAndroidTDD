package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state: State = State.Initial

    private lateinit var textView: TextView
    private lateinit var linearLayout: LinearLayout
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.titleTextView)
        button = findViewById(R.id.removeButton)
        linearLayout = findViewById(R.id.rootLayout)


        button.setOnClickListener {
            state = State.Removed
            state.apply(linearLayout, textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            state = savedInstanceState.getSerializable(KEY, State::class.java) as State
        } else {
            state = savedInstanceState.getSerializable(KEY) as State
        }
        state.apply(linearLayout, textView)
    }

    companion object {
        private const val KEY = "key"
    }

    interface State : Serializable {

        fun apply(linearLayout: LinearLayout, textView: TextView)

        object Initial : State {
            override fun apply(linearLayout: LinearLayout, textView: TextView) = Unit
        }

        object Removed : State {
            override fun apply(linearLayout: LinearLayout, textView: TextView) {
                linearLayout.removeView(textView)
            }
        }
    }
}