package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails

class FetchMailsRepositoryImpl(
    private val fetchMails: FetchMails,
    private val mapper: MessageDtoMapper
) : FetchMailsRepository {

    override fun fetchMails(username: String, password: String, first: Int, last: Int): Single<List<MyMessage>> {
        return Single.just(
            mapper.mapToDomainModelList(fetchMails.fetchingMails(username, password, first, last)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingGet()
            )
        )
    }
}