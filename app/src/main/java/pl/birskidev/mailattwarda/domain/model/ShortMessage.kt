package pl.birskidev.mailattwarda.domain.model

import javax.mail.internet.MimeBodyPart

class ShortMessage(
    var id: Int? = null,
    val title: String? = null,
    val date: String? = null,
    val sender: String? = null,
    val time: String? = null,
    val hasAttachments: Boolean? = false,
)