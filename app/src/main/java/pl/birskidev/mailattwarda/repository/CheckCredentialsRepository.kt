package pl.birskidev.mailattwarda.repository


interface CheckCredentialsRepository {

    suspend fun checkCredentialsRepository(username: String, password: String) : Boolean
}