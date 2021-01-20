package pl.birskidev.mailattwarda.network.response

import pl.birskidev.mailattwarda.util.pop3Host
import pl.birskidev.mailattwarda.util.provider
import java.io.IOException
import java.util.*
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Store

class CheckCredentialsImpl : CheckCredentials {

    override suspend fun checkCredentials(username: String, password: String): Boolean {
        val props = Properties()
        try {
            val session: Session = Session.getInstance(props)
            val store: Store = session.getStore(provider)
            store.connect(pop3Host, username, password)

            store.close()

            return true
        } catch (ex: MessagingException) {
            ex.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return false
    }
}