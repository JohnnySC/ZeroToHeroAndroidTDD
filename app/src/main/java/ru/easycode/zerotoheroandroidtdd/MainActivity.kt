import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var linearLayout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.titleTextView)
        val button = findViewById<Button>(R.id.removeButton)
        linearLayout = findViewById(R.id.rootLayout)

        savedInstanceState.let {
            val removeTextView = savedInstanceState?.getBoolean(KEY)
            if (removeTextView == true) linearLayout.removeView(textView)
        }

        button.setOnClickListener {
            linearLayout.removeView(textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val removeTextView = linearLayout.childCount == 1
        outState.putBoolean(KEY, removeTextView)
    }

    companion object {
        private const val KEY = "key"
    }
