package ru.surfstudio.standard.f_characters.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.standard.f_characters.R
import ru.surfstudio.standard.f_characters.search.SearchEvent.CharactersLoad
import ru.surfstudio.standard.f_characters.search.SearchEvent.Input
import ru.surfstudio.standard.f_characters.search.controllers.CharacterController
import ru.surfstudio.standard.f_characters.databinding.FragmentSearchBinding
import ru.surfstudio.standard.f_characters.search.di.SearchScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.recylcer.PaginationFooterItemController
import ru.surfstudio.standard.ui.util.performIfChanged
import ru.surfstudio.standard.ui.util.setOnTextChanged
import javax.inject.Inject

/**
 * Вью таба поиск
 */
internal class SearchFragmentView : BaseMviFragmentView<SearchState, SearchEvent>(), CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<SearchEvent>

    @Inject
    override lateinit var sh: SearchScreenStateHolder

    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun createConfigurator() = SearchScreenConfigurator(arguments)

    private val paginationController = PaginationFooterItemController()
    private val charactersController = CharacterController()

    private val easyAdapter = EasyPaginationAdapter(paginationController) {
        CharactersLoad.LoadMore.emit()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun bind() {
        super.bind()
        initViews()
    }

    override fun render(state: SearchState) {
        renderCharacters(state)
        renderPlaceholder(state)
    }

    private fun renderPlaceholder(state: SearchState) {
        if (state.charactersRequestUi.isLoading) {
            binding.placeholderContainer.performIfChanged(state.lastSearchQuery) {
                isVisible = true
            }
        } else {
            binding.placeholderContainer.isVisible = false
        }
    }

    override fun initViews() {
        binding.charactersRv.adapter = easyAdapter
        binding.serachEt.setOnTextChanged { Input.SearchCharacterEvent(it).emit() }
    }

    private fun renderCharacters(state: SearchState) {
        state.charactersRequestUi.data?.safeGet { characters, paginationState ->
            val items = if (state.lastSearchQuery.isNotBlank()) {
                ItemList.create(characters, charactersController)
            } else {
                ItemList.create()
            }

            easyAdapter.setItems(items, paginationState)


            binding.charactersRv.performIfChanged(state.lastSearchQuery) {
                if (state.lastSearchQuery.isBlank() || characters.isEmpty()) {
                    isVisible = false
                } else {
                    post { scrollToPosition(0) }
                    isVisible = true
                }
            }

            binding.noCharactersTv.isVisible = characters.isEmpty()
        }
    }
}