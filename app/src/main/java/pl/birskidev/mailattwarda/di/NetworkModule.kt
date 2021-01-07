package pl.birskidev.mailattwarda.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.mapper.util.MyMessageUtil
import pl.birskidev.mailattwarda.network.response.FetchMails
import pl.birskidev.mailattwarda.network.response.FetchMailsImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMessageDtoMapper(myMessageUtil: MyMessageUtil) : MessageDtoMapper {
        return MessageDtoMapper(myMessageUtil)
    }

    @Singleton
    @Provides
    fun provideFetchMails() : FetchMails {
        return FetchMailsImpl()
    }

    @Singleton
    @Provides
    fun provideMyMessageUtil() : MyMessageUtil {
        return MyMessageUtil()
    }

}