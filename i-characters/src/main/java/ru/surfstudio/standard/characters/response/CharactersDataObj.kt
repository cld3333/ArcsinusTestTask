package ru.surfstudio.standard.characters.response

import com.google.gson.annotations.SerializedName

data class CharactersDataObj(
        @SerializedName("count")
        val count: Int,
        @SerializedName("results")
        val resultsObj: List<CharactersObj>
)
