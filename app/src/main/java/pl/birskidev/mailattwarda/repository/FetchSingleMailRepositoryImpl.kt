package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails

class FetchSingleMailRepositoryImpl(
    private val fetchMails: FetchMails,
    private val mapper: MessageDtoMapper
) : FetchSingleMailRepository {
    override suspend fun fetchSingleMail(
        username: String,
        password: String,
        first: Int,
        last: Int,
        isShortMessage: Boolean
    ): List<MyMessage> {

        return mapper.mapToDomainModelList(fetchMails.fetchingMails(username, password, first, last, isShortMessage))
    }
}