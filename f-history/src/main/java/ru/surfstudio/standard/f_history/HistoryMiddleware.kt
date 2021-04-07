package ru.surfstudio.standard.f_history

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.rx.extension.observeScreenResult
import ru.surfstudio.android.rx.extension.toObservable
import ru.surfstudio.standard.f_history.HistoryEvent.Navigation
import ru.surfstudio.standard.ui.dialog.base.SimpleResult
import ru.surfstudio.standard.ui.dialog.simple.SimpleDialogRoute
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.show
import javax.inject.Inject

@PerScreen
internal class HistoryMiddleware @Inject constructor(
        basePresenterDependency: BaseMiddlewareDependency,
        private val screenResultObserver: ScreenResultObserver,
        private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<HistoryEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<HistoryEvent>): Observable<out HistoryEvent> = transformations(eventStream) {
        addAll(
            Navigation::class decomposeTo navigationMiddleware,
            HistoryEvent.OpenDialog::class eventMapTo { showDialog() },
            observeDialogResult()
        )
    }

    private fun showDialog(): Observable<out HistoryEvent> =
            Navigation().show(createDialogRoute()).toObservable()

    private fun observeDialogResult(): Observable<out HistoryEvent> {
        return screenResultObserver
                .observeScreenResult(createDialogRoute())
                .map {
                    HistoryEvent.ShowDialogResult(
                            if (it == SimpleResult.POSITIVE) {
                                "ok tapped"
                            } else {
                                "cancel tapped"
                            }
                    )
                }
    }

    private fun createDialogRoute() =
            SimpleDialogRoute(
                    dialogId = "simple_dialog",
                    message = "Quick brown fox jumps over the lazy dog",
                    title = "Simple dialog"
            )
}