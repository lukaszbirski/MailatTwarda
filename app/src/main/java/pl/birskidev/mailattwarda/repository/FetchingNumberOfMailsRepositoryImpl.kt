package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.network.mapper.ChipMapper
import pl.birskidev.mailattwarda.network.response.FetchNumberOfMails

class FetchingNumberOfMailsRepositoryImpl(
        private val mapper: ChipMapper,
        private val fetchNumberOfMails: FetchNumberOfMails
) : FetchingNumberOfMailsRepository {

    override suspend fun fetchNumberOfMails(username: String, password: String): List<MyChip> {
        return mapper.mapToDomainModel(fetchNumberOfMails.fetchingNumberOfMails(username, password))
    }
}