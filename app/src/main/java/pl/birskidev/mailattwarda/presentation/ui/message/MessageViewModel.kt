package pl.birskidev.mailattwarda.presentation.ui.message

import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.birskidev.mailattwarda.domain.model.MyMessage


class MessageViewModel
@ViewModelInject
constructor(): ViewModel() {

    private val mutableSelectedMessage = MutableLiveData<MyMessage>()
    val selectedMessage: LiveData<MyMessage> get() = mutableSelectedMessage

    fun selectMessage(myMessage: MyMessage) {
        mutableSelectedMessage.value = myMessage
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

    fun getRecipients(): String {
        return "${selectedMessage.value?.recipients.toString()}"
    }

    fun getMessageContent(): Spanned {
        return HtmlCompat.fromHtml(selectedMessage.value?.content.toString(), 0)
    }

    init {

    }

}