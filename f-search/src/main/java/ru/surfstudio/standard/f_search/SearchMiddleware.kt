package ru.surfstudio.standard.f_search

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.characters.CharactersInteractor
import ru.surfstudio.standard.f_search.SearchEvent.*
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import javax.inject.Inject

@PerScreen
internal class SearchMiddleware @Inject constructor(
        basePresenterDependency: BaseMiddlewareDependency,
        private val charactersInteractor: CharactersInteractor,
        private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<SearchEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<SearchEvent>): Observable<out SearchEvent> = transformations(eventStream) {
        addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.BtnClick::class eventMapTo ::getCharactersRequestEvent
        )
    }

    private fun getCharactersRequestEvent(event: Input.BtnClick) =
            charactersInteractor.getCharacters()
                    .io()
                    .asRequestEvent(::GetCharactersRequestEvent)
}