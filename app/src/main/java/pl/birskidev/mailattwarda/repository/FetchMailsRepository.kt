package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.ShortMessage

interface FetchMailsRepository {

    suspend fun fetchMails (username: String, password: String, first: Int, last: Int, isShortMessage: Boolean) : List<ShortMessage>
}