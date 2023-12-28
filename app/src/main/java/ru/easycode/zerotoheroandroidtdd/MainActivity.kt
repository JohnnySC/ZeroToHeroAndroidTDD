package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            inputEditText.addTextChangedListener {
                actionButton.isEnabled = inputEditText.text.toString().length >= 3
            }

            actionButton.setOnClickListener {
                titleTextView.text = inputEditText.text.toString()
                inputEditText.text?.clear()
            }
        }
    }
}