package pl.birskidev.mailattwarda.presentation.ui.new_message

import android.util.Log
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.request.SendMail
import javax.inject.Named

class NewMessageViewModel
@ViewModelInject
constructor(
    @Named("login") private val login: String,
    @Named("person") private val person: String,
    @Named("password") private val password: String,
    private val sendMail: SendMail
): ViewModel() {

    private val disposable = CompositeDisposable()

    var recipient: String? = null
    var subject: String? = null
    var message: String? = null
    var ccRecipient: String? = null
    var emailTo = mutableListOf<String>()
    var emailCC = mutableListOf<String>()

    private var callback: NewMessageListener = NewMessageFragment()

    fun selectMessage(myMessage: MyMessage) {
        recipient = myMessage.sender
        subject = "RE: ${myMessage.title}"
        message = createTemplateRespond(myMessage)
    }

    fun sendEmail(view: View) {

        //makes toast when email address is not added
        if (recipient.isNullOrEmpty()) {
            callback.toastMessage(
                view,
                view.context.resources.getString(R.string.address_required_string)
            )
            return
        }

        //if subject was not added then (no topic) string will be added
        if (subject.isNullOrEmpty()) subject = view.context.resources.getString(R.string.no_topic_string)

        //is message is null then " " is added
        if (message.isNullOrEmpty()) message = ""

        //create list of email addresses to send as TO
        val addressesTo = recipient!!.split(", ")
        addressesTo.getShortWords(emailTo, 254)

        //create list of email addresses to send as CC
        if (!ccRecipient.isNullOrEmpty()) {
            val addressesCC = ccRecipient!!.split(", ")
            addressesCC.getShortWords(emailCC, 254)
        }

        //shows progressdialog before sending email
        callback.setProgressDialog(view)


        //sending mail
        disposable.add(
            sendMail.sendMail(emailTo, emailCC, subject.toString(), message.toString(), login, password, person)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        //if email is send progressdialog is dissmissed, toast showed and user navigated to messageListFragment
                        callback.dismissProgressDialog(view)
                        callback.toastMessage(
                            view,
                            view.context.resources.getString(R.string.email_was_sent_string)
                        )
                        Thread.sleep(1500)
                        Navigation.findNavController(view).navigate(R.id.action_newMessageFragment_to_messageListFragment)
                    }

                    override fun onError(e: Throwable?) {
                        //if error occured progressdialog is dissmissed and toast informing about error showed
                        callback.dismissProgressDialog(view)
                        callback.toastMessage(
                            view,
                            view.context.resources.getString(R.string.error_while_sending_email)
                        )
                    }
                })
        )
    }

    //return template for the respond containing message info
    private fun createTemplateRespond(myMessage: MyMessage): String {

        return "\n\n-----Original Message-----\nFrom: ${myMessage.sender}\n" +
                "Sent: ${myMessage.date},${myMessage.time}\n" +
                "Subject: ${myMessage.title}\n" +
                "To: ${myMessage.recipients.toString()}\n\n" +
                "${myMessage.content}"
    }

    //converts string into list of emails
    private fun List<String>.getShortWords(shortWords: MutableList<String>, maxLength: Int) {
        this.filterTo(shortWords) { it.length <= maxLength }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}