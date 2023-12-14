package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var liveData: LiveDataWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        liveData = (application as App).liveData

        binding.actionButton.setOnClickListener {
            val text = (binding.inputEditText.text).toString()
            liveData.update(text)
            binding.inputEditText.text?.clear()
        }

        liveData.liveData().observe(this) {strings ->
            Log.d("ml", "LiveData observer")
            removeTextViews(binding.contentLayout)
            strings?.forEach {
                val textView = TextView(this)
                textView.freezesText = true
                textView.text = it
                binding.contentLayout.addView(textView)
            }
        }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putCharSequenceArrayList(KEY, arrayList)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        arrayList = savedInstanceState.getCharSequenceArrayList(KEY)!!
//         }
//    }
    }

    private fun removeTextViews(layout: LinearLayout){
        val elements = layout.childCount
        Log.d("ml", "children = $elements")
        layout.removeViews(1, elements-1)
    }

    companion object {
        private const val KEY = "key"
    }

}