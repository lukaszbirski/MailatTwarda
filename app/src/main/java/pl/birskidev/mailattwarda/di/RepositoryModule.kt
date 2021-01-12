package pl.birskidev.mailattwarda.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.birskidev.mailattwarda.network.mapper.ChipMapper
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.response.FetchMails
import pl.birskidev.mailattwarda.network.response.FetchNumberOfMails
import pl.birskidev.mailattwarda.repository.FetchMailsRepository
import pl.birskidev.mailattwarda.repository.FetchMailsRepositoryImpl
import pl.birskidev.mailattwarda.repository.FetchingNumberOfMailsRepository
import pl.birskidev.mailattwarda.repository.FetchingNumberOfMailsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFetchMailsRepository(
        messageDtoMapper: MessageDtoMapper,
        fetchMails: FetchMails
    ): FetchMailsRepository {
        return FetchMailsRepositoryImpl(
            fetchMails, messageDtoMapper
        )
    }

    @Singleton
    @Provides
    fun provideFetchNumberOfMailsRepository(
            chipMapper: ChipMapper,
            fetchingNumberOfMails: FetchNumberOfMails
    ): FetchingNumberOfMailsRepository {
        return FetchingNumberOfMailsRepositoryImpl(
                chipMapper, fetchingNumberOfMails
        )
    }
}