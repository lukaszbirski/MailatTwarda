package pl.birskidev.mailattwarda.repository

import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails
import javax.mail.Message

class FetchSingleMailRepositoryImpl(
    private val fetchMails: FetchMails,
    private val mapper: MessageDtoMapper
) : FetchSingleMailRepository {
    override fun fetchSingleMail(
        username: String,
        password: String,
        first: Int,
        last: Int,
        isShortMessage: Boolean
    ): Single<List<MyMessage>> {

        return Single.just(
            mapper.mapToDomainModelList(fetchMails.fetchingMails(username, password, first, last, isShortMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingGet()
            )
        )
    }
}