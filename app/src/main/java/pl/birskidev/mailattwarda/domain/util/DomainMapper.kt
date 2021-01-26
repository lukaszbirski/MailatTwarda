package pl.birskidev.mailattwarda.domain.util

interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(entity: T) : DomainModel
}

