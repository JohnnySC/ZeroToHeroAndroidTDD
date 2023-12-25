package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as App).viewModel
        viewModel.liveData().observe(this) { uiState ->
            uiState.apply(
                button = binding.actionButton,
                textView = binding.titleTextView,
                progressBar = binding.progressBar
            )
        }

        binding.actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}