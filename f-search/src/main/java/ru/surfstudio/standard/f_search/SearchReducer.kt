package ru.surfstudio.standard.f_search

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.f_search.SearchEvent.*
import ru.surfstudio.standard.ui.mvi.mapper.RequestMappers
import ru.surfstudio.standard.ui.mvi.pagination.PaginationBundle
import javax.inject.Inject

internal data class SearchState(
        val charactersRequestUi: RequestUi<PaginationBundle<Character>> = RequestUi(),
)

/**
 * State Holder [SearchFragmentView]
 */
@PerScreen
internal class SearchScreenStateHolder @Inject constructor(
) : State<SearchState>(SearchState())

/**
 * Reducer [SearchFragmentView]
 */
@PerScreen
internal class SearchReducer @Inject constructor(
        dependency: BaseReactorDependency
) : BaseReducer<SearchEvent, SearchState>(dependency) {

    override fun reduce(state: SearchState, event: SearchEvent): SearchState =
            when (event) {
                is GetCharactersRequestEvent -> onCharacterLoad(event, state)
                else -> state
            }

    private fun onCharacterLoad(event: GetCharactersRequestEvent, state: SearchState): SearchState =
        state.copy(charactersRequestUi = RequestMapper.builder(event.request, state.charactersRequestUi)
                .mapData(RequestMappers.data.pagination())
                .mapLoading(RequestMappers.loading.simple())
                .handleError(RequestMappers.error.loadingBased(errorHandler))
                .build()
        )

}