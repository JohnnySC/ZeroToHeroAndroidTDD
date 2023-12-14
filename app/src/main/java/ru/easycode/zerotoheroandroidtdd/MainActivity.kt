package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var liveData: MutableLiveData<ArrayList<CharSequence>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        liveData = (application as App).liveData

        binding.actionButton.setOnClickListener {
            val textView = TextView(this)
            val text = (binding.inputEditText.text).toString()
            liveData.value!!.add(text)
            textView.text = text
            binding.contentLayout.addView(textView)
            binding.inputEditText.text?.clear()
        }

        liveData.observe(this) {
            it.forEach {
                val textView = TextView(this)
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

    companion object {
        private const val KEY = "key"
    }

}