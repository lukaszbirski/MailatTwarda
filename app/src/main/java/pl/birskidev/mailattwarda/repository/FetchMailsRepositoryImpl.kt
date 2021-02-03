package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.domain.model.ShortMessage
import pl.birskidev.mailattwarda.network.mapper.ShortMessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails

class FetchMailsRepositoryImpl(
    private val fetchMails: FetchMails,
    private val mapper: ShortMessageDtoMapper
) : FetchMailsRepository {

    override fun fetchMails(username: String, password: String, first: Int, last: Int, isShortMessage: Boolean): Single<List<ShortMessage>> {
        return Single.just(
            mapper.mapToDomainModelList(fetchMails.fetchingMails(username, password, first, last, isShortMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingGet()
            )
        )
    }
}