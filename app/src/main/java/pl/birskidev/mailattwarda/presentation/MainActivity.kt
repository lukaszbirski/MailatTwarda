package pl.birskidev.mailattwarda.presentation

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        AppCenter.start(
            application, "53a5ece4-4e87-43c4-a20b-bbaea7e77943",
            Analytics::class.java, Crashes::class.java
        )
    }
}