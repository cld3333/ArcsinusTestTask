package ru.surfstudio.standard.f_characters.history

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.rx.extension.observeScreenResult
import ru.surfstudio.android.rx.extension.toObservable
import ru.surfstudio.standard.characters.CharactersCache
import ru.surfstudio.standard.f_characters.history.HistoryEvent.*
import ru.surfstudio.standard.ui.dialog.base.SimpleResult
import ru.surfstudio.standard.ui.dialog.simple.SimpleDialogRoute
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.show
import javax.inject.Inject

@PerScreen
internal class HistoryMiddleware @Inject constructor(
        basePresenterDependency: BaseMiddlewareDependency,
        private val navigationMiddleware: NavigationMiddleware,
        private val charactersCache: CharactersCache
) : BaseMiddleware<HistoryEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<HistoryEvent>): Observable<out HistoryEvent> = transformations(eventStream) {
        addAll(
                onCreate() map { ShowCharacters(charactersCache.all) },
                Navigation::class decomposeTo navigationMiddleware,
        )
    }

}