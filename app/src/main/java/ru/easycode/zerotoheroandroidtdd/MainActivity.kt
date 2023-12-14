package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.databinding.ListItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = (application as App).mainViewModel

        binding.actionButton.setOnClickListener {
            mainViewModel.addText(binding.inputEditText.text.toString())
            binding.inputEditText.text?.clear()
        }

        mainViewModel.liveData().observe(this) {strings ->
            Log.d("ml", "LiveData observer")
            binding.contentLayout.removeAllViews()
            fillParent(strings)
        }
    }

    private fun fillParent(strings: ArrayList<CharSequence>) {
        strings.forEach {
            val textViewBinding = ListItemBinding.inflate(layoutInflater)
            val textView = textViewBinding.root
            textView.text = it
            binding.contentLayout.addView(textView)
        }
    }
}