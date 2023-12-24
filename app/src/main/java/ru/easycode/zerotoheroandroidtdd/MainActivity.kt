package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var actionButton: Button

    private val viewModel = MainViewModel(
        Repository.Base(), LiveDataWrapper.Base())
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = findViewById(R.id.titleTextView)
        progressBar = findViewById(R.id.progressBar)
        actionButton = findViewById(R.id.actionButton)

        viewModel.liveData().observe(this) {
                uiState -> uiState.apply(actionButton, progressBar, title)
        }

        actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}