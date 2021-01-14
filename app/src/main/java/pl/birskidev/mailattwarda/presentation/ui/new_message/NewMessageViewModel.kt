package pl.birskidev.mailattwarda.presentation.ui.new_message

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.network.request.SendMail

class NewMessageViewModel
@ViewModelInject
constructor(
    private val sendMail: SendMail
): ViewModel() {

    private val disposable = CompositeDisposable()

    var recipient: String? = null
    var subject: String? = null
    var message: String? = null
    private var callback: NewMessageListener = NewMessageFragment()

    fun sendEmail(view: View) {

        if (recipient.isNullOrEmpty()) {
            callback.toastMessage(view, view.context.resources.getString(R.string.address_required_string))
            return
        }
        if (subject.isNullOrEmpty()) subject = R.string.no_topic_string.toString()
        if (message.isNullOrEmpty()) message = ""

        disposable.add(
                sendMail.sendMail(recipient.toString(), subject.toString(), message.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableCompletableObserver() {
                            override fun onComplete() {
                                callback.toastMessage(view, view.context.resources.getString(R.string.email_was_sent_string))
                            }

                            override fun onError(e: Throwable?) {
                                callback.toastMessage(view, view.context.resources.getString(R.string.error_while_sending_email))
                            }
                        })
        )

        Navigation.findNavController(view).navigate(R.id.action_newMessageFragment_to_messageListFragment)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}