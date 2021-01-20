package pl.birskidev.mailattwarda.network.response

import io.reactivex.Single

interface CheckCredentials {

    fun checkCredentials(username: String, password: String) : Single<Boolean>
}