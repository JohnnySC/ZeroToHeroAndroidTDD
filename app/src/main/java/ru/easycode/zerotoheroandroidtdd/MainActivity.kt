package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val changeButton = findViewById<Button>(R.id.changeButton)
        val textView = findViewById<TextView>(R.id.titleTextView)
        changeButton.setOnClickListener {
            textView.text = "I am an Android Developer!"
        }
    }
}