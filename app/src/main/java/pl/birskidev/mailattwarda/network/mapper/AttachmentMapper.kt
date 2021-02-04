package pl.birskidev.mailattwarda.network.mapper

import pl.birskidev.mailattwarda.domain.model.Attachment
import pl.birskidev.mailattwarda.domain.model.ShortMessage
import pl.birskidev.mailattwarda.domain.util.DomainMapper
import javax.mail.Message
import javax.mail.internet.MimeBodyPart

class AttachmentMapper : DomainMapper<MimeBodyPart, Attachment> {

    override fun mapToDomainModel(entity: MimeBodyPart): Attachment {
        return Attachment(
            title = entity.fileName,
            attachment = entity
        )
    }

    fun mapToDomainModelList(initial: List<MimeBodyPart>): List<Attachment> {
        return initial.map { mapToDomainModel(it)}
    }
}