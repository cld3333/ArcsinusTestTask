package ru.surfstudio.standard.application.characters.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.surfstudio.standard.characters.CharactersApi
import ru.surfstudio.standard.characters.CharactersCache

@Module
class CharactersModule {

    @Provides
    @PerApplication
    internal fun provideAuthApi(retrofit: Retrofit): CharactersApi =
            retrofit.create(CharactersApi::class.java)

    @Provides
    @PerApplication
    internal fun provideEmployeeCache(
            context: Context,
            namingProcessor: NamingProcessor,
    ): CharactersCache =
            CharactersCache(
                    AppDirectoriesProvider.provideNoBackupStorageDir(context),
                    namingProcessor
            )
}