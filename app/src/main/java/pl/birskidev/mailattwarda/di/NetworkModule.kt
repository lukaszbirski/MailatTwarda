package pl.birskidev.mailattwarda.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.birskidev.mailattwarda.network.mapper.ChipMapper
import pl.birskidev.mailattwarda.network.mapper.MessageDtoMapper
import pl.birskidev.mailattwarda.network.mapper.util.MyMessageUtil
import pl.birskidev.mailattwarda.network.request.SendMail
import pl.birskidev.mailattwarda.network.request.SendMailImp
import pl.birskidev.mailattwarda.network.response.*
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
    fun provideChipMapper() : ChipMapper {
        return ChipMapper()
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

    @Singleton
    @Provides
    fun provideSendMail() : SendMail {
        return SendMailImp()
    }

    @Singleton
    @Provides
    fun provideFetchingNumberOfMails() : FetchNumberOfMails {
        return FetchNumberOfMailsImpl()
    }

    @Singleton
    @Provides
    fun provideCheckCredentials() : CheckCredentials {
        return CheckCredentialsImpl()
    }
}