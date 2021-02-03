package pl.birskidev.mailattwarda.network.mapper

import pl.birskidev.mailattwarda.domain.model.ShortMessage
import pl.birskidev.mailattwarda.domain.util.DomainMapper
import pl.birskidev.mailattwarda.network.mapper.util.MyMessageUtil
import javax.mail.Message

class ShortMessageDtoMapper(myMessageUtil: MyMessageUtil) :DomainMapper<Message, ShortMessage> {

    private val myMessageUtil = myMessageUtil

    override fun mapToDomainModel(entity: Message): ShortMessage {
        return ShortMessage(
            id = entity.messageNumber,
            title = entity.subject,
            sender = myMessageUtil.formatEmail(entity.from[0].toString()),
            date = myMessageUtil.formatDate(entity.sentDate),
            time = myMessageUtil.formatTime(entity.sentDate),
            hasAttachments = myMessageUtil.hasAttachments(entity)
        )
    }

    fun mapToDomainModelList(initial: List<Message>): List<ShortMessage> {
        return initial.map { mapToDomainModel(it)}
    }

}