package ru.surfstudio.standard.f_history

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

internal data class HistoryState(
        val message: String = EMPTY_STRING
)

/**
 * State Holder [HistoryFragmentView]
 */
@PerScreen
internal class HistoryScreenStateHolder @Inject constructor(
) : State<HistoryState>(HistoryState())

/**
 * Reducer [HistoryFragmentView]
 */
@PerScreen
internal class HistoryReducer @Inject constructor(
        dependency: BaseReactorDependency
) : BaseReducer<HistoryEvent, HistoryState>(dependency) {

    override fun reduce(state: HistoryState, event: HistoryEvent): HistoryState {
        return when (event) {
            is HistoryEvent.ShowDialogResult -> {
                state.copy(message = event.message)
            }
            else -> state
        }
    }
}