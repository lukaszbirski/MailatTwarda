package pl.birskidev.mailattwarda.network.response

import io.reactivex.Single
import javax.mail.Message

interface FetchMails {

    fun fetchingMails(username: String, password: String) : Single<List<Message>>
}