package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import pl.birskidev.mailattwarda.domain.model.MyChip

interface FetchingNumberOfMailsRepository {

    fun fetchNumberOfMails (username: String, password: String) : Single<List<MyChip>>
}