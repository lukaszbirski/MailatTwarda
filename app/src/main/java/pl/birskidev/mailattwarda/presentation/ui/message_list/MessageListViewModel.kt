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

class MessageListViewModel
@ViewModelInject
constructor(
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
//        fetchMails()
    }

//    fun fetchMails() {
//        loading.postValue(true)
//        disposable.add(
//            repository.fetchMails("", "")
//                ?.subscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread())
//                ?.subscribeWith(object : DisposableSingleObserver<List<MyMessage>>() {
//                    override fun onSuccess(t: List<MyMessage>) {
//                        Log.d(TAG, "onSuccess: ${t.size}")
//                        _messages.postValue(t)
//                        loading.postValue(false)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.d(TAG, "onError: ")
//                    }
//                })
//        )
//    }

    private fun fetchNumberOfMails() {
        disposable.add(
                numberOfMailsRepository.fetchNumberOfMails("", "")
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