package ru.surfstudio.standard.characters.response

import com.google.gson.annotations.SerializedName

data class CharactersDataObj(
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("total")
        val total: Int,
        @SerializedName("count")
        val count: Int,
        @SerializedName("results")
        val resultsObj: List<CharactersObj>,
)
