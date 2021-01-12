package pl.birskidev.mailattwarda.network.response

import io.reactivex.Single

interface FetchNumberOfMails {

    fun fetchingNumberOfMails(username: String, password: String) : Single<Int>
}