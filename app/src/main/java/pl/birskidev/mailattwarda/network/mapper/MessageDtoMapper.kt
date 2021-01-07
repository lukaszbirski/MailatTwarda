package pl.birskidev.mailattwarda.network.mapper

import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.domain.util.DomainMapper
import javax.mail.Message

class MessageDtoMapper : DomainMapper<Message, MyMessage> {

    override fun mapToDomainModel(entity: Message): MyMessage {
        return MyMessage(
            title = entity.subject,
            content = null,
            sender = null,
            recipients = listOf(),
            date = null,
            time = null,
            hasAttachments = false
        )
    }

    fun mapToDomainModelList(initial: List<Message>): List<MyMessage> {
        return initial.map { mapToDomainModel(it) }
    }
}