package ru.surfstudio.standard.characters.response

import com.google.gson.annotations.SerializedName

data class CharactersObj(
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("thumbnail")
        val thumbnail: CharactersThumbnail
)