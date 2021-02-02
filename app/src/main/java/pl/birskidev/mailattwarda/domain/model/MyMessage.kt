package pl.birskidev.mailattwarda.domain.model

import javax.mail.internet.MimeBodyPart


data class MyMessage(
    val title: String? = null,
    val content: String? = null,
    val sender: String? = null,
    val recipients: List<String>? = listOf(),
    val date: String? = null,
    val time: String? = null,
    val hasAttachments: Boolean? = false,
    val attachments: List<MimeBodyPart>? = listOf()
)
