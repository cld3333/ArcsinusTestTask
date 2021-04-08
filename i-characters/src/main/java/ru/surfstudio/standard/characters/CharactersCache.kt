package ru.surfstudio.standard.characters

import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage
import ru.surfstudio.standard.domain.characters.Character
import javax.inject.Inject

private const val CHARACTER_CACHE_KEY = "CHARACTER_CACHE_KEY"
private const val CHARACTER_CACHE_LIMIT = 2000

@PerApplication
class CharactersCache @Inject constructor(
        cacheDir: String,
        namingProcessor: NamingProcessor,
) : BaseJsonFileStorage<Character>(
        FileProcessor(cacheDir, CHARACTER_CACHE_KEY, CHARACTER_CACHE_LIMIT),
        namingProcessor,
        Character::class.java
) {

    fun putAll(employees: List<Character>) {
        employees.forEach { put(it.name, it) }
    }
}