package pl.birskidev.mailattwarda.network.mapper

import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.domain.util.DomainMapper
import pl.birskidev.mailattwarda.network.mapper.util.MyMessageUtil
import javax.mail.Message

class MessageDtoMapper(myMessageUtil: MyMessageUtil) : DomainMapper<Message, MyMessage> {

    private val myMessageUtil = myMessageUtil

    override fun mapToDomainModel(entity: Message): MyMessage {
        return MyMessage(
            title = entity.subject,
            content = myMessageUtil.getTextFromMessage(entity),
            sender = myMessageUtil.formatEmail(entity.from[0].toString()),
            recipients = myMessageUtil.getToRecipients(entity),
            date = myMessageUtil.formatDate(entity.sentDate),
            time = myMessageUtil.formatTime(entity.sentDate),
            hasAttachments = myMessageUtil.hasAttachments(entity)
        )
    }

    fun mapToDomainModelList(initial: List<Message>): List<MyMessage> {
        return initial.map { mapToDomainModel(it) }
    }
}