package pl.birskidev.mailattwarda.network.request

import io.reactivex.Completable
import pl.birskidev.mailattwarda.util.smtpsHost
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendMailImp : SendMail {

    override fun sendMail(emailsTo: List<String>, subject: String, message: String,login: String, password: String, person: String): Completable {

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

            //Creating array of recipients
            val recipientsList = mutableListOf<Address>()
            for (emailTo in emailsTo) {
                recipientsList.add(InternetAddress(emailTo))
            }
            val to : Array<Address> = recipientsList.toTypedArray()

            try {
                val sender: Address = InternetAddress(login, person)

                MimeMessage(session).let { msg ->
                    msg.setText(message)
                    msg.setFrom(sender)
                    msg.setRecipients(Message.RecipientType.TO, to)
                    msg.subject = subject
                    Transport.send(msg, login, password)
                }

            } catch (e: MessagingException) {
                emitter.onError(e)
            }

            //ending subscription
            emitter.onComplete()
        }
    }

}