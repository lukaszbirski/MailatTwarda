package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails

class FetchMailsRepositoryImpl(
    private val fetchMails: FetchMails,
    private val mapper: MessageDtoMapper
) : FetchMailsRepository {

    override suspend fun fetchMails(username: String, password: String, first: Int, last: Int): List<MyMessage> {
        return mapper.mapToDomainModelList(fetchMails.fetchingMails(username, password, first, last))
    }
}