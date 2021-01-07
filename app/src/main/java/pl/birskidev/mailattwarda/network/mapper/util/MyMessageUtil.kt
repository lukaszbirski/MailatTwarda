package pl.birskidev.mailattwarda.network.mapper.util

import java.text.SimpleDateFormat
import java.util.*
import javax.mail.Message
import javax.mail.Multipart

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

}