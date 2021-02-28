package pl.birskidev.mailattwarda.network.mapper.util

import java.text.SimpleDateFormat
import java.util.*
import javax.mail.*
import javax.mail.internet.ContentType
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMultipart
import kotlin.collections.ArrayList

class MyMessageUtil {

    fun formatEmail(email: String): String? {
        return email.substringAfter('<').substringBefore('>')
    }

    fun hasAttachments(message: Message): Boolean {
        if (message.isMimeType("multipart/mixed")) {
            val message = message.content as Multipart
            if (message.count > 1) return true
        }
        return false
    }

    fun formatDate(date: Date): String {
        var formatterDayMonth = SimpleDateFormat("dd MMM yyyy")
        return formatterDayMonth.format(date)
    }

    fun formatTime(date: Date): String {
        var formatterDayMonth = SimpleDateFormat("HH:mm")
        return formatterDayMonth.format(date)
    }

    fun getTextFromMessage(message: Message): String {
        var result: String = ""
        if (message.isMimeType("text/plain")) {
            result = message.content.toString()
        }
        else if (message.isMimeType("multipart/*")) {
            val mimeMultipart = message.content as MimeMultipart
            result = getTextFromMimeMultipart(mimeMultipart)
        }
        else if(message.isMimeType("text/html")){
            result = message.content.toString()
        }
        return result
    }

    private fun getTextFromMimeMultipart(mimeMultipart: MimeMultipart): String {
        val count = mimeMultipart.count
        if (count == 0) throw MessagingException("Multipart with no body parts not supported.")

        val multipartRelated = ContentType(mimeMultipart.contentType).match("multipart/related")

        if(multipartRelated){
            val part = mimeMultipart.getBodyPart(0)
            val multipartAlt = ContentType(part.contentType).match("multipart/alternative")
            if(multipartAlt) {
                return getTextFromMimeMultipart(part.content as MimeMultipart)
            }
        } else {
            val multipartAlt = ContentType(mimeMultipart.contentType).match("multipart/alternative")
            if (multipartAlt) {
                for (i in 0 until count) {
                    val part = mimeMultipart.getBodyPart(i)
                    if (part.isMimeType("text/html")) {
                        return getTextFromBodyPart(part)
                    }
                }
            }
        }

        var result: String = ""
        for (i in 0 until count) {
            val bodyPart = mimeMultipart.getBodyPart(i)
            result += getTextFromBodyPart(bodyPart)
        }
        return result
    }

    private fun getTextFromBodyPart(bodyPart: BodyPart): String {
        var result: String = ""
        if (bodyPart.isMimeType("text/plain")) {
            result = bodyPart.content as String
        } else if (bodyPart.isMimeType("text/html")) {
            val html = bodyPart.content as String
            result = html
        } else if (bodyPart.content is MimeMultipart) {
            result = getTextFromMimeMultipart(bodyPart.content as MimeMultipart)
        }
        return result
    }

    fun getRecipients(message: Message, recipientType: Message.RecipientType): List<String>? {
        val toAddresses: MutableList<String> = ArrayList()
        val recipients = message.getRecipients(recipientType)
        return if (recipients != null) {
            for (address in recipients) {
                formatEmail(address.toString())?.let { toAddresses.add(it) }
            }
            toAddresses
        } else null
    }

    fun getAllAttachments(message: Message): List<MimeBodyPart>? {
        val messageAttachments: MutableList<MimeBodyPart>? = ArrayList()
        val contentType = message.contentType

        if (contentType.contains("multipart")) {

            val multiPart = message.content as Multipart

            for (i in 0 until multiPart.count) {
                val part = multiPart.getBodyPart(i) as MimeBodyPart
                if (Part.ATTACHMENT.equals(part.disposition, ignoreCase = true)) {
                    messageAttachments?.add(part)
                }
            }
        }

        return messageAttachments
    }

}