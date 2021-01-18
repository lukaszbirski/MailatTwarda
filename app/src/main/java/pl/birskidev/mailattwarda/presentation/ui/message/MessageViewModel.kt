package pl.birskidev.mailattwarda.presentation.ui.message

import android.util.Log
import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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

    init {

    }

}