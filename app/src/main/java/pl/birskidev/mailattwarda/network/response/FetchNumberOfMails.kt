package pl.birskidev.mailattwarda.network.response

interface FetchNumberOfMails {

    suspend fun fetchingNumberOfMails(username: String, password: String) : Int
}