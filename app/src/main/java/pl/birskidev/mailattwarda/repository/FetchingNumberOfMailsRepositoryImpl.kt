package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.network.mapper.ChipMapper
import pl.birskidev.mailattwarda.network.response.FetchNumberOfMails

class FetchingNumberOfMailsRepositoryImpl(
        private val mapper: ChipMapper,
        private val fetchNumberOfMails: FetchNumberOfMails
) : FetchingNumberOfMailsRepository {


    override fun fetchNumberOfMails(username: String, password: String): Single<List<MyChip>> {
        return Single.just(
            mapper.mapToDomainModel(fetchNumberOfMails.fetchingNumberOfMails(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingGet()
            )
        )
    }
}