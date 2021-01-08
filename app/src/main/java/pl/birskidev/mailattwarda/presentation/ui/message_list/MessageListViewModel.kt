package pl.birskidev.mailattwarda.presentation.ui.message_list

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.request.SendMailImp
import pl.birskidev.mailattwarda.repository.FetchMailsRepository
import pl.birskidev.mailattwarda.util.TAG

class MessageListViewModel
@ViewModelInject
constructor(
    private val repository: FetchMailsRepository
): ViewModel() {

    private val disposable = CompositeDisposable()

    private val _messages: MutableLiveData<List<MyMessage>> = MutableLiveData()
    val messages: LiveData<List<MyMessage>> get() = _messages

    init {
        fetchMails()
    }

    private fun fetchMails() {
        disposable.add(
            repository.fetchMails("", "")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<List<MyMessage>>() {
                    override fun onSuccess(t: List<MyMessage>) {
                        Log.d(TAG, "onSuccess: ${t.size}")
                        _messages.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: ")
                    }
                })
        )
    }

    fun sendEmail() {
        disposable.add(
            SendMailImp.sendMail("lukasz.birski@gmail.com", "TEST", "TEST")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable?) {
                    }

                })
        )
    }
}