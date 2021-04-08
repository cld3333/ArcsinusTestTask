package ru.surfstudio.standard.domain.characters

import java.io.Serializable

data class Character(
        val name: String,
        val description: String,
        val imageUrl: String
) : Serializable