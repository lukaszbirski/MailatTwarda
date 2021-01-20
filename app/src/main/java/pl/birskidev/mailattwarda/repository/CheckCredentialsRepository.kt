package pl.birskidev.mailattwarda.repository

import io.reactivex.Single

interface CheckCredentialsRepository {

    fun checkCredentialsRepository(username: String, password: String) : Single<Boolean>
}