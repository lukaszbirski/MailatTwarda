package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.domain.model.ShortMessage

interface FetchMailsRepository {

    fun fetchMails (username: String, password: String, first: Int, last: Int, isShortMessage: Boolean) : Single<List<ShortMessage>>
}