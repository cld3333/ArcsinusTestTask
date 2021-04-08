package ru.surfstudio.standard.f_search

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
import ru.surfstudio.standard.f_search.SearchEvent.CharactersLoad
import ru.surfstudio.standard.f_search.SearchEvent.Input
import ru.surfstudio.standard.f_search.controllers.CharacterController
import ru.surfstudio.standard.f_search.databinding.FragmentSearchBinding
import ru.surfstudio.standard.f_search.di.SearchScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.recylcer.PaginationFooterItemController
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
        state.charactersRequestUi.data?.safeGet { characters, paginationState ->
            val items = if (state.lastSearchQuery.isNotBlank()) {
                ItemList.create(characters, charactersController)
            } else {
                ItemList.create()
            }

            easyAdapter.setItems(items, paginationState)
            binding.charactersRv.apply {
                isVisible = state.lastSearchQuery.isNotBlank()
                post { scrollToPosition(0) }
            }

        }
    }

    override fun initViews() {
        binding.charactersRv.adapter = easyAdapter
        binding.serachEt.setOnTextChanged { Input.SearchCharacterEvent(it).emit() }
    }
}