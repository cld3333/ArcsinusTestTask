package ru.surfstudio.standard.f_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.fragment_search.*
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.item.BindableItem
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.standard.f_search.SearchEvent.*
import ru.surfstudio.standard.f_search.controllers.CharacterController
import ru.surfstudio.standard.f_search.databinding.FragmentSearchBinding
import ru.surfstudio.standard.f_search.di.SearchScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.recylcer.PaginationFooterItemController
import ru.surfstudio.standard.ui.util.performIfChanged
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
        CharactersLoad.NextPage.emit()
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
            easyAdapter.setItems(ItemList.create(characters, charactersController), paginationState)
        }
    }

    override fun initViews() {
        binding.charactersRv.adapter = easyAdapter
        binding.button.clicks().emit(Input.BtnClick)
    }
}