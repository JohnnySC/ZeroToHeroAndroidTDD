package ru.easycode.zerotoheroandroidtdd

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        val button = findViewById<Button>(R.id.hideButton)

        textView.visibility = savedInstanceState.let {
            it?.getInt("key") ?: View.VISIBLE
        }

        button.setOnClickListener {
            textView.visibility = View.INVISIBLE
        }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("key", View.INVISIBLE )
    }


}