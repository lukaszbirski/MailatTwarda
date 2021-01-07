package pl.birskidev.mailattwarda.network

import io.reactivex.Single
import pl.birskidev.mailattwarda.util.pop3Host
import pl.birskidev.mailattwarda.util.provider
import java.util.*
import javax.mail.Folder
import javax.mail.Message
import javax.mail.Session
import javax.mail.Store
import kotlin.system.exitProcess

class FetchMailsImpl : FetchMails {

    override fun fetchingMails(username: String, password: String): Single<List<Message>> {
        val props = Properties()

        // Connect to the POP3 server
        val session: Session = Session.getInstance(props)
        val store: Store = session.getStore(provider)
        store.connect(pop3Host, username, password)

        // Open the folder
        val inbox: Folder = store.getFolder("INBOX") ?: exitProcess(1)
        inbox.open(Folder.READ_ONLY)

        val total = inbox.messageCount

        // Get the messages from the server
        val messagesInReverse: Array<Message> = inbox.getMessages(total - 20 + 1, total)
        val messages: Array<Message> = messagesInReverse.reversedArray()

        // Close the connection
        // but don't remove the messages from the server
//        inbox.close(false)
//        store.close()
        return Single.just(mutableListOf(*messages))
    }

}