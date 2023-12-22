package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var title: TextView
    private lateinit var actionButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        title = findViewById(R.id.titleTextView)
        actionButton = findViewById(R.id.actionButton)

//        actionButton.setOnClickListener {
//            actionButton.isEnabled = false
//            progressBar.isVisible = true
//            actionButton.postDelayed({
//                progressBar.isVisible = false
//                actionButton.isEnabled = true
//                title.text = "hello"
//            }, 3500)
//        }

//        actionButton.setOnClickListener {
//            actionButton.isEnabled = false
//            progressBar.isVisible = true
//            Handler(Looper.getMainLooper()).postDelayed({
//                title.text = "hello"
//                progressBar.isVisible = false
//                actionButton.isEnabled = true
//            }, 3500)
//        }


        actionButton.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        progressBar.isVisible = true
        actionButton.isEnabled = false
        startAction {
            title.text = it
            progressBar.isVisible = false
            actionButton.isEnabled = true
        }
    }

    private fun startAction(callback: (String) -> Unit) {
        try {
            Thread {
                Handler(Looper.getMainLooper()).postDelayed({
                    callback.invoke("hello")
                }, 3500)
            }.start()

        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

}