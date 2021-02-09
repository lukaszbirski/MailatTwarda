package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.MyMessage

interface FetchSingleMailRepository {

    suspend fun fetchSingleMail (username: String, password: String, first: Int, last: Int, isShortMessage: Boolean) : List<MyMessage>

}