package pl.birskidev.mailattwarda.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}