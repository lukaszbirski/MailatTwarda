package pl.birskidev.mailattwarda.presentation.ui.message

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.text.Spanned
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import pl.birskidev.mailattwarda.BuildConfig
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.domain.model.MyMessage
import java.io.File
import java.util.*


class MessageViewModel
@ViewModelInject
constructor() : ViewModel() {

    private val mutableSelectedMessage = MutableLiveData<MyMessage>()
    val selectedMessage: LiveData<MyMessage> get() = mutableSelectedMessage

    private val mutableContext = MutableLiveData<Context>()
    val context: LiveData<Context> get() = mutableContext

    fun selectMessage(myMessage: MyMessage) {
        mutableSelectedMessage.value = myMessage
    }

    fun selectContext(context: Context?) {
        mutableContext.value = context
    }

    fun getFrom(): String {
        return "${selectedMessage.value?.sender.toString()}"
    }

    fun getDate(): String {
        return "${selectedMessage.value?.date.toString()}"
    }

    fun getTime(): String {
        return "${selectedMessage.value?.time.toString()}"
    }

    fun getRecipients(): String? {
        var recipientsString = ""
        if (selectedMessage.value?.recipients.isNullOrEmpty()) return recipientsString

        for (recipient in selectedMessage.value?.recipients!!) {
            recipientsString = "$recipientsString; $recipient"
        }
        return recipientsString.substring(2)
    }

    fun getMessageContent(): Spanned {
        return HtmlCompat.fromHtml(selectedMessage.value?.content.toString(), 0)
    }

    fun respondToMessage(view: View) {
        val bundle = bundleOf("myMessage" to selectedMessage.value)
        Navigation.findNavController(view).navigate(
            R.id.action_messageFragment_to_newMessageFragment,
            bundle
        )
    }

    fun downloadMessages(view: View) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        val title = "Choose app to open the attachment"

        val path = context.value!!.cacheDir
        val fileName = selectedMessage.value!!.attachments?.get(0)?.fileName

        selectedMessage.value!!.attachments?.get(0)?.saveFile("$path/$fileName")

        val file = File("$path$fileName")

        val uri: Uri? = FileProvider.getUriForFile(
            Objects.requireNonNull(context.value!!.applicationContext),
            BuildConfig.APPLICATION_ID + ".provider", file
        )

        intent.setDataAndType(uri, "application/pdf");

        val pm: PackageManager? = context.value!!.packageManager

        val resInfoList: List<ResolveInfo> = pm!!.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.value!!.grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        if (intent.resolveActivity(pm!!) != null) {
            context.value!!.startActivity(intent);
        }
    }

}