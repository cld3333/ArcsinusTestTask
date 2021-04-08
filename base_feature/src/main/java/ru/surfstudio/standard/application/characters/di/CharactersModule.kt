package ru.surfstudio.standard.application.characters.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.characters.CharactersApi

@Module
class CharactersModule {

    @Provides
    @PerApplication
    internal fun provideAuthApi(retrofit: Retrofit): CharactersApi =
            retrofit.create(CharactersApi::class.java)
}