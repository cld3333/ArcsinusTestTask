package ru.surfstudio.standard.application.cache.di

import ru.surfstudio.android.filestorage.naming.NamingProcessor

/**
 * Дефолтный NamingProcessor
 */
class DefaultNamingProcessor : NamingProcessor {

    override fun getNameFrom(rawName: String): String = rawName
}