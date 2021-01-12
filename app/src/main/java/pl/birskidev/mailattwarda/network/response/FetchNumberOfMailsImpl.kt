package pl.birskidev.mailattwarda.network.response

import io.reactivex.Single
import pl.birskidev.mailattwarda.util.pop3Host
import pl.birskidev.mailattwarda.util.provider
import java.util.*
import javax.mail.Folder
import javax.mail.Session
import javax.mail.Store
import kotlin.system.exitProcess

class FetchNumberOfMailsImpl  : FetchNumberOfMails {

    override fun fetchingNumberOfMails(username: String, password: String): Single<Int> {
        val props = Properties()

        // Connect to the POP3 server
        val session: Session = Session.getInstance(props)
        val store: Store = session.getStore(provider)
        store.connect(pop3Host, username, password)

        // Open the folder
        val inbox: Folder = store.getFolder("INBOX") ?: exitProcess(1)
        inbox.open(Folder.READ_ONLY)

        val total = inbox.messageCount

        inbox.close(false)
        store.close()

        return Single.just(total)
    }


}