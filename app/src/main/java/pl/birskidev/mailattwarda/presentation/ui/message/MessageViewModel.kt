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
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.birskidev.mailattwarda.BuildConfig
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.domain.model.Attachment
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.mapper.AttachmentMapper
import pl.birskidev.mailattwarda.repository.FetchMailsRepository
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.mail.internet.MimeBodyPart

@HiltViewModel
class MessageViewModel
@Inject
constructor(
    private val mapper: AttachmentMapper
) : ViewModel() {

    private val _selectedMessage = MutableLiveData<MyMessage>()
    val selectedMessage: LiveData<MyMessage> get() = _selectedMessage

    private val _context = MutableLiveData<Context>()
    val context: LiveData<Context> get() = _context

    private val _attachments: MutableLiveData<List<Attachment>> = MutableLiveData()
    val attachments: LiveData<List<Attachment>> get() = _attachments

    fun selectContext(context: Context?) {
        _context.value = context
    }

    fun selectMessage(myMessage: MyMessage) {
        _selectedMessage.value = myMessage
        _attachments.postValue(mapper.mapToDomainModelList(myMessage.attachments!!))
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

    fun getCCRecipients(): String? {
        var recipientsString = ""
        if (selectedMessage.value?.ccRecipients.isNullOrEmpty()) return recipientsString
        for (recipient in selectedMessage.value?.ccRecipients!!) {
            recipientsString = "$recipientsString; $recipient"
        }
        return recipientsString.substring(2)
    }

    fun getMessageContent(): Spanned {
        return HtmlCompat.fromHtml(selectedMessage.value?.content.toString(), 0)
    }

    fun respondToMessage(view: View) {
        Navigation.findNavController(view).navigate(
            R.id.action_messageFragment_to_newMessageFragment
        )
    }

    fun downloadMessages(mimeBodyPart: MimeBodyPart) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        val path = context.value!!.cacheDir
        val fileName = selectedMessage.value!!.attachments?.get(0)?.fileName

        mimeBodyPart.saveFile("$path/$fileName")

        val file = File("$path/$fileName")

        val uri: Uri? = FileProvider.getUriForFile(
            Objects.requireNonNull(context.value!!.applicationContext),
            BuildConfig.APPLICATION_ID + ".fileprovider", file
        )

        intent.setDataAndType(uri, selectFileType(file))

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

    //todo potencially expand list
    private fun selectFileType(file: File): String{
        when (file.extension) {
            "pdf" -> return "application/pdf"
            "docx" -> return "application/msword"
            "doc" -> return "application/msword"
            else -> return "application/*"
        }
    }
}