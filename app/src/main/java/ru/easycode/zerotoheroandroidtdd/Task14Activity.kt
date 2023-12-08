package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import ru.easycode.zerotoheroandroidtdd.task14.UiState

class Task14Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task14)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val actionButton = findViewById<Button>(R.id.actionButton)

        UiState.Initial.show(titleTextView, progressBar, actionButton)

        actionButton.setOnClickListener {
            UiState.Loading.show(titleTextView, progressBar, actionButton)
            Thread{
                Thread.sleep(1000)
                runOnUiThread {
                    UiState.Base.show(titleTextView, progressBar, actionButton)
                }
            }.start()
        }
    }


}

