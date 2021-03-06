package ru.surfstudio.standard.f_characters.history

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.standard.domain.characters.Character
import javax.inject.Inject

internal data class HistoryState(
        val characters: List<Character> = emptyList()
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

    override fun reduce(state: HistoryState, event: HistoryEvent): HistoryState =
            when (event) {
                is HistoryEvent.ShowCharacters -> state.copy(characters = event.characters)
                else -> state
            }
}