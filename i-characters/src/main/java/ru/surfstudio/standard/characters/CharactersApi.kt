package ru.surfstudio.standard.characters

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.surfstudio.standard.characters.response.CharactersResponse
import ru.surfstudio.standard.i_network.CharactersUrls.GET_CHARACTERS

interface CharactersApi {

    @GET(GET_CHARACTERS)
    fun getCharacters(
            @Query("nameStartsWith") nameStartsWith: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int,
    ): Single<CharactersResponse>
}