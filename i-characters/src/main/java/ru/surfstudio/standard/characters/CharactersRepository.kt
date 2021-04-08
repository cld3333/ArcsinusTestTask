package ru.surfstudio.standard.characters

import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.i_network.network.transform
import ru.surfstudio.standard.i_network.service.BaseNetworkService
import javax.inject.Inject

/**
 * Сервис, отвечающий за авторизацию и регистрацию пользователя
 */
@PerApplication
class CharactersRepository @Inject constructor(
        private val authApi: CharactersApi
) : BaseNetworkService() {

    fun getCharacters(): Single<List<Character>> =
            authApi.getCharacters().transform()

}