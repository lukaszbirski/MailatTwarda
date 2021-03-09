package pl.birskidev.mailattwarda.presentation.ui.message_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.presentation.ui.new_message.NewMessageFragment
import pl.birskidev.mailattwarda.presentation.ui.new_message.NewMessageListener
import pl.birskidev.mailattwarda.repository.FetchMailsRepository
import pl.birskidev.mailattwarda.repository.FetchingNumberOfMailsRepository
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MessageListViewModel
@Inject
constructor(
    @Named("login") private val login: String,
    @Named("person") private val person: String,
    @Named("password") private val password: String,
    private val repository: FetchMailsRepository,
    private val numberOfMailsRepository: FetchingNumberOfMailsRepository
): ViewModel() {

    private val _messages: MutableLiveData<List<MyMessage>> = MutableLiveData()
    val messages: LiveData<List<MyMessage>> get() = _messages

    private val _chips: MutableLiveData<List<MyChip>> = MutableLiveData()
    val chips: LiveData<List<MyChip>> get() = _chips

    val loading = MutableLiveData<Boolean>()

    init {
        GlobalScope.launch {
            fetchNumberOfMails()
            refresh()
        }
    }

    private suspend fun refresh() {
        fetchMails(1, 15)
    }

    fun loadMessages(first: Int, last: Int){
        GlobalScope.launch {
            fetchMails(first, last)
        }
    }

    private suspend fun fetchMails(first: Int, last: Int) {
        loading.postValue(true)
        val result = repository.fetchMails(login, password, first, last)
        _messages.postValue(result)
        loading.postValue(false)
    }

    private suspend fun fetchNumberOfMails() {
        val result = numberOfMailsRepository.fetchNumberOfMails(login, password)
        _chips.postValue(result)
    }
}