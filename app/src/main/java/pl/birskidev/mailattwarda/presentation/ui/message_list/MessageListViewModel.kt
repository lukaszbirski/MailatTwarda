package pl.birskidev.mailattwarda.presentation.ui.message_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.domain.model.ShortMessage
import pl.birskidev.mailattwarda.repository.FetchMailsRepository
import pl.birskidev.mailattwarda.repository.FetchingNumberOfMailsRepository
import javax.inject.Named

class MessageListViewModel
@ViewModelInject
constructor(
    @Named("login") private val login: String,
    @Named("person") private val person: String,
    @Named("password") private val password: String,
    private val repository: FetchMailsRepository,
    private val numberOfMailsRepository: FetchingNumberOfMailsRepository
): ViewModel() {

    private val _messages: MutableLiveData<List<ShortMessage>> = MutableLiveData()
    val messages: LiveData<List<ShortMessage>> get() = _messages

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
        val result = repository.fetchMails(login, password, first, last, true)
        _messages.postValue(result)
        loading.postValue(false)
    }

    private suspend fun fetchNumberOfMails() {
        val result = numberOfMailsRepository.fetchNumberOfMails(login, password)
        _chips.postValue(result)
    }
}