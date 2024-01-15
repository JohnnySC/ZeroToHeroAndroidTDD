package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLoad = findViewById<Button>(R.id.actionButton)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        buttonLoad.setOnClickListener{
             buttonLoad.isEnabled = false
            progressBar.visibility = View.VISIBLE
            buttonLoad.postDelayed({
                titleTextView.visibility =View.VISIBLE
                progressBar.visibility= View.GONE
                buttonLoad.isEnabled = true
            },3000)
        }
    }
}