package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = (application as App).viewModel

        viewModel.liveData().observe(this) { uiState ->
            uiState.apply(
                binding.actionButton,
                binding.titleTextView,
                binding.progressBar
            )
        }

        binding.actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}