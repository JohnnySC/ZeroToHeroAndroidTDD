package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var arrayList: ArrayList<CharSequence> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionButton.setOnClickListener {
            val textView = TextView(this)
            val text = (binding.inputEditText.text).toString()
            arrayList.add(text)
            textView.text = text
            binding.contentLayout.addView(textView)
            binding.inputEditText.text?.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequenceArrayList(KEY, arrayList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        arrayList = savedInstanceState.getCharSequenceArrayList(KEY)!!
        arrayList.forEach {
            val textView = TextView(this)
            textView.text = it
            binding.contentLayout.addView(textView)
        }
    }

    companion object {
        private const val KEY = "key"
    }
}