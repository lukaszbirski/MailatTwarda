package pl.birskidev.mailattwarda.repository

import io.reactivex.Single;
import pl.birskidev.mailattwarda.domain.model.MyMessage
import javax.mail.Message

interface FetchSingleMailRepository {

    fun fetchSingleMail (username: String, password: String, first: Int, last: Int, isShortMessage: Boolean) : Single<List<MyMessage>>

}