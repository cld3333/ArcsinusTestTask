package ru.surfstudio.standard.characters

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.i_network.network.BaseNetworkInteractor
import javax.inject.Inject

@PerApplication
@SuppressLint("CheckResult")
class CharactersInteractor @Inject constructor(
        private val charactersRepository: CharactersRepository,
        private val charactersCache: CharactersCache,
        connectionQualityProvider: ConnectionProvider,
) : BaseNetworkInteractor(connectionQualityProvider) {

    fun getCharacters(nameStartsWith: String, offset: Int): Single<DataList<Character>> =
            charactersRepository.getCharacters(nameStartsWith, offset)
                    .doOnSuccess(charactersCache::putAll)
}