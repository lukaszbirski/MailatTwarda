package pl.birskidev.mailattwarda.network.response


interface CheckCredentials {

    suspend fun checkCredentials(username: String, password: String) : Boolean
}