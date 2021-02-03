package pl.birskidev.mailattwarda.network.response

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

    override fun fetchingMails(username: String, password: String, first: Int, last: Int, isShortMessage: Boolean): Single<List<Message>> {
        val props = Properties()

        // Connect to the POP3 server
        val session: Session = Session.getInstance(props)
        val store: Store = session.getStore(provider)
        store.connect(pop3Host, username, password)

        // Open the folder
        val inbox: Folder = store.getFolder("INBOX") ?: exitProcess(1)
        inbox.open(Folder.READ_ONLY)

        var start: Int? = null
        var end: Int?  = null

        if (isShortMessage){
            val total = inbox.messageCount
            end = total - first + 1
            start = total - last + 1

        } else {
            start = first
            end = last
        }

        // Get the messages from the server
        val messagesInReverse: Array<Message> = inbox.getMessages(start, end)
        val messages: Array<Message> = messagesInReverse.reversedArray()

        // Close the connection
        // but don't remove the messages from the server
//        inbox.close(false)
//        store.close()
        return Single.just(mutableListOf(*messages))
    }

}