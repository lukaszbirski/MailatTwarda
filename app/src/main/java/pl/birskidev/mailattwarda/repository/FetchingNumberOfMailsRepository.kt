package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.MyChip

interface FetchingNumberOfMailsRepository {

    suspend fun fetchNumberOfMails (username: String, password: String) : List<MyChip>
}