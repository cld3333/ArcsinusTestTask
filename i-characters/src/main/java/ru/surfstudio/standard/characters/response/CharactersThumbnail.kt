package ru.surfstudio.standard.characters.response

import com.google.gson.annotations.SerializedName

data class CharactersThumbnail(
        @SerializedName("path")
        val path: String,
        @SerializedName("extension")
        val extension: String
)
