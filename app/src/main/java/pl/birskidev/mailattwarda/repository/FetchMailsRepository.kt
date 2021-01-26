package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import pl.birskidev.mailattwarda.domain.model.MyMessage

interface FetchMailsRepository {

    fun fetchMails (username: String, password: String, first: Int, last: Int) : Single<List<MyMessage>>
}