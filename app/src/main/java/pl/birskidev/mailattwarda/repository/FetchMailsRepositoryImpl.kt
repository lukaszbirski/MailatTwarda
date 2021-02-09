package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.ShortMessage
import pl.birskidev.mailattwarda.network.mapper.ShortMessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails

class FetchMailsRepositoryImpl(
    private val fetchMails: FetchMails,
    private val mapper: ShortMessageDtoMapper
) : FetchMailsRepository {

    override suspend fun fetchMails(username: String, password: String, first: Int, last: Int, isShortMessage: Boolean): List<ShortMessage> {
        return mapper.mapToDomainModelList(fetchMails.fetchingMails(username, password, first, last, isShortMessage))
    }
}