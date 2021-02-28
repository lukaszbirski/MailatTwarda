package pl.birskidev.mailattwarda.domain.model

import javax.mail.internet.MimeBodyPart

data class Attachment(
    val title: String? = null,
    val attachment: MimeBodyPart? = null
)
