package ru.surfstudio.standard.characters

import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.i_network.service.BaseNetworkService
import javax.inject.Inject

private const val DEFAULT_GET_CHARACTERS_LIMIT = 10

@PerApplication
class CharactersRepository @Inject constructor(
        private val authApi: CharactersApi
) : BaseNetworkService() {

    fun getCharacters(nameStartsWith: String, offset: Int): Single<DataList<Character>> =
            authApi.getCharacters(nameStartsWith, offset, DEFAULT_GET_CHARACTERS_LIMIT)
                    .map {
                        DataList(
                                it.transform().sortedBy(Character::name),
                                DEFAULT_GET_CHARACTERS_LIMIT,
                                it.data.offset,
                                it.data.total
                        )
                    }

}