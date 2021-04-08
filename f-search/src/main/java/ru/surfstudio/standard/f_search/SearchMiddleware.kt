package ru.surfstudio.standard.f_search

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.characters.CharactersInteractor
import ru.surfstudio.standard.f_search.SearchEvent.*
import ru.surfstudio.standard.f_search.SearchEvent.CharactersLoad.FirsLoading
import ru.surfstudio.standard.f_search.SearchEvent.CharactersLoad.LoadMore
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import javax.inject.Inject

@PerScreen
internal class SearchMiddleware @Inject constructor(
        basePresenterDependency: BaseMiddlewareDependency,
        private val charactersInteractor: CharactersInteractor,
        private val navigationMiddleware: NavigationMiddleware,
        private val sh: SearchScreenStateHolder
) : BaseMiddleware<SearchEvent>(basePresenterDependency) {

    val state: SearchState get() = sh.value

    override fun transform(eventStream: Observable<SearchEvent>): Observable<out SearchEvent> = transformations(eventStream) {
        addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.SearchCharacterEvent::class mapTo { FirsLoading(it.query) },
                CharactersLoad::class eventMapTo ::getCharacters
        )
    }

    private fun getCharacters(event: CharactersLoad): Observable<out GetCharactersRequestEvent> {
        var searchQuery = ""
        var offset = 0

        when (event) {
            is FirsLoading -> {
                searchQuery = event.searchQuery
            }
            is LoadMore -> {
                searchQuery = state.lastSearchQuery
                offset = state.charactersRequestUi.data?.data?.nextOffset
                        ?: 0
            }
        }

        return charactersInteractor.getCharacters(searchQuery, offset)
                .io()
                .asRequestEvent(::GetCharactersRequestEvent)
    }
}