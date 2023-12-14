package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

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
            removeTextViews(binding.contentLayout)
            fillParent(strings)
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

    private fun fillParent(strings: ArrayList<CharSequence>) {
        strings?.forEach {
            val textView = TextView(this)
            textView.freezesText = true
            textView.text = it
            binding.contentLayout.addView(textView)
        }
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