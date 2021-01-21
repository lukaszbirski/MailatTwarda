package pl.birskidev.mailattwarda.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.birskidev.mailattwarda.presentation.BaseApplication
import pl.birskidev.mailattwarda.util.*
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) = app.getSharedPreferences(
        SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    @Named("person")
    fun providePerson(sharedPreferences: SharedPreferences) = sharedPreferences.getString(
        KEY_PERSON, "") ?: ""

    @Singleton
    @Provides
    @Named("login")
    fun provideLogin(sharedPreferences: SharedPreferences) = sharedPreferences.getString(
        KEY_LOGIN, "") ?: ""

    @Singleton
    @Provides
    @Named("password")
    fun providePassword(sharedPreferences: SharedPreferences) = sharedPreferences.getString(
        KEY_PASSWORD, "") ?: ""
}