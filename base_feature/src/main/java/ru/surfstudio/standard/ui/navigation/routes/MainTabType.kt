package ru.surfstudio.standard.ui.navigation.routes

import java.io.Serializable

/**
 * Типы табов на главном экране MainActivityView
 * TODO переименовать табы и добавить свои при необходимости
 */
enum class MainTabType: Serializable {
    HISTORY, // экран итории просмотров
    SEARCH, // экран поиска
}