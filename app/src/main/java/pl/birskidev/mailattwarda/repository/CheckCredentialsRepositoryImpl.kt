package pl.birskidev.mailattwarda.repository

import pl.birskidev.mailattwarda.network.response.CheckCredentials

class CheckCredentialsRepositoryImpl(
    private val checkCredentials: CheckCredentials
) : CheckCredentialsRepository{

    override suspend fun checkCredentialsRepository(username: String, password: String): Boolean {
        return checkCredentials.checkCredentials(username, password)
    }
}