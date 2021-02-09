package pl.birskidev.mailattwarda.network.response

import javax.mail.Message

interface FetchMails {

    suspend fun fetchingMails(username: String, password: String, first: Int, last: Int, isShortMessage: Boolean) : List<Message>
}