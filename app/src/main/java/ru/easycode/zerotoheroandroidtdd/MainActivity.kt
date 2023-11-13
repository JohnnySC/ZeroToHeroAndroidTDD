package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.titleTextView)
        val changeButton = findViewById<Button>(R.id.changeButton)

        if(savedInstanceState != null) {
            textView.text = savedInstanceState.getString(KEY_STRING)
        }

        changeButton.setOnClickListener {
            textView.text = "I am an Android Developer!"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_STRING, textView.text.toString())
    }

//    override fun onRestoreInstanceState(
//        savedInstanceState: Bundle
//    ) {
//        super.onRestoreInstanceState(savedInstanceState)
//        textView.text = savedInstanceState.getString("key")
//    }

    companion object {
        private const val KEY_STRING = "key"
    }
}