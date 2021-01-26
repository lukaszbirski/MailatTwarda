package pl.birskidev.mailattwarda.presentation.ui.message

import android.text.Spanned
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import pl.birskidev.mailattwarda.R
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

    fun respondToMessage(view: View){
        val bundle = bundleOf("myMessage" to selectedMessage.value)
        Navigation.findNavController(view).navigate(R.id.action_messageFragment_to_newMessageFragment, bundle)
    }

}