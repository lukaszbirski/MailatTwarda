package pl.birskidev.mailattwarda.presentation.ui.message_list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.repository.FetchMailsRepository
import pl.birskidev.mailattwarda.repository.FetchingNumberOfMailsRepository
import pl.birskidev.mailattwarda.util.TAG
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

    private val disposable = CompositeDisposable()

    private val _messages: MutableLiveData<List<MyMessage>> = MutableLiveData()
    val messages: LiveData<List<MyMessage>> get() = _messages

    private val _chips: MutableLiveData<List<MyChip>> = MutableLiveData()
    val chips: LiveData<List<MyChip>> get() = _chips

    val loading = MutableLiveData<Boolean>()

    init {
        fetchNumberOfMails()
        refresh()
    }

    private fun refresh() {
        fetchMails(1, 15)
    }

    fun fetchMails(first: Int, last: Int) {
        loading.postValue(true)
        disposable.add(
            repository.fetchMails(login, password, first, last)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<List<MyMessage>>() {
                    override fun onSuccess(t: List<MyMessage>) {
                        Log.d(TAG, "onSuccess: ${t.size}")
                        _messages.postValue(t)
                        loading.postValue(false)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: ")
                    }
                })
        )
    }

    private fun fetchNumberOfMails() {
        disposable.add(
                numberOfMailsRepository.fetchNumberOfMails(login, password)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribeWith(object : DisposableSingleObserver<List<MyChip>>() {
                            override fun onSuccess(value: List<MyChip>?) {
                                Log.d(TAG, "onSuccess: ${value?.size}")
                                _chips.postValue(value)
                            }

                            override fun onError(e: Throwable?) {

                            }

                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}