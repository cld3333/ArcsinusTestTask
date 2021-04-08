package ru.surfstudio.standard.characters

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.i_network.network.BaseNetworkInteractor
import javax.inject.Inject

/**
 * Интерактор, отвечающий за авторизацию пользователя
 */
@PerApplication
@SuppressLint("CheckResult")
class CharactersInteractor @Inject constructor(
        private val charactersRepository: CharactersRepository,
        connectionQualityProvider: ConnectionProvider,
) : BaseNetworkInteractor(connectionQualityProvider) {

    fun getCharacters(): Single<List<Character>> =
            charactersRepository.getCharacters()
}