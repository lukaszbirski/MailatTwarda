package pl.birskidev.mailattwarda.network.request

import android.util.Log
import io.reactivex.Completable
import pl.birskidev.mailattwarda.util.TAG
import pl.birskidev.mailattwarda.util.smtpsHost
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendMailImp {

    companion object {

        fun sendMail(emailTo: String, subject: String, message: String): Completable {
            return Completable.create { emitter ->

                //configure SMTP server
                val props: Properties = Properties().also {
                    it["mail.smtp.host"] = smtpsHost
                    it["mail.transport.protocol"] = "smtps"
                    it["mail.smtp.starttls.enable"] = "true"
                    it["mail.smtp.ssl.trust"] = smtpsHost
                }

                //Creating a session
                val session = Session.getInstance(props)

                try {
                    val sender: Address = InternetAddress("l.birski@twarda.pan.pl", "Łukasz Birski")
                    val recipient: Address = InternetAddress(emailTo)

                    MimeMessage(session).let { msg ->
                        msg.setText(message)
                        msg.setFrom(sender)
                        msg.setRecipient(Message.RecipientType.TO, recipient)
                        msg.subject = subject
                        Transport.send(msg, "", "")
                        Log.d(TAG, "Message to $recipient have been sent!")
                    }

                } catch (e: MessagingException) {
                    emitter.onError(e)
                    Log.d(TAG, "Error while sending message!")
                }

                //ending subscription
                emitter.onComplete()
            }
        }

    }


}