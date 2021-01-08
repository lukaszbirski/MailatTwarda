package pl.birskidev.mailattwarda.presentation.ui.new_message

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.network.request.SendMail

class NewMessageViewModel
@ViewModelInject
constructor(
    private val sendMail: SendMail
): ViewModel() {

    private val disposable = CompositeDisposable()

    fun sendEmail() {
        disposable.add(
                sendMail.sendMail("lukasz.birski@gmail.com", "TEST", "TEST")
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}