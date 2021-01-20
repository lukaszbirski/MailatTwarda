package pl.birskidev.mailattwarda.repository

import io.reactivex.Single
import pl.birskidev.mailattwarda.network.response.CheckCredentials

class CheckCredentialsRepositoryImpl(
    private val checkCredentials: CheckCredentials
) : CheckCredentialsRepository{

    override fun checkCredentialsRepository(username: String, password: String): Single<Boolean> {
        return checkCredentials.checkCredentials(username, password)
    }
}