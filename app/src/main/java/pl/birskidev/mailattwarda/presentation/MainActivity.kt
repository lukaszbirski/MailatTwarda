package pl.birskidev.mailattwarda.presentation

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.presentation.util.ConnectionLiveData

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        connectionLiveData.observe(this, { isConnected ->
            if (!isConnected) {
    
            }
         })

//        AppCenter.start(
//            application, "53a5ece4-4e87-43c4-a20b-bbaea7e77943",
//            Analytics::class.java, Crashes::class.java
//        )
    }
}