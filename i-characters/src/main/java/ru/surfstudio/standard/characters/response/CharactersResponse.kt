package ru.surfstudio.standard.characters.response

import com.google.gson.annotations.SerializedName
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.i_network.network.Transformable

private const val DEFAULT_IMAGE_TYPE = "/portrait_xlarge."

data class CharactersResponse(
        @SerializedName("data")
        val data: CharactersDataObj
) : Transformable<List<Character>> {

    override fun transform() =
            data.resultsObj.map {
                Character(
                        it.name,
                        it.description,
                        it.thumbnail.path + DEFAULT_IMAGE_TYPE + it.thumbnail.extension
                )
            }

}
