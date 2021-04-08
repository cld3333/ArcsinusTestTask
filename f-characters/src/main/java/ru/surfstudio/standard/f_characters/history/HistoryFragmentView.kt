package ru.surfstudio.standard.f_characters.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.message.MessageController
import ru.surfstudio.standard.f_characters.R
import ru.surfstudio.standard.f_characters.databinding.FragmentHistoryBinding
import ru.surfstudio.standard.f_characters.history.di.HistoryScreenConfigurator
import ru.surfstudio.standard.f_characters.search.controllers.CharacterController
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.util.performIfChanged
import javax.inject.Inject

internal class HistoryFragmentView : BaseMviFragmentView<HistoryState, HistoryEvent>(), CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<HistoryEvent>

    @Inject
    override lateinit var sh: HistoryScreenStateHolder

    @Inject
    lateinit var messageController: MessageController

    private val charactersController = CharacterController()

    private val easyAdapter = EasyAdapter()

    private val binding by viewBinding(FragmentHistoryBinding::bind)

    override fun createConfigurator() = HistoryScreenConfigurator(arguments)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun render(state: HistoryState) {
        binding.charactersRv.performIfChanged(state.characters) {
            easyAdapter.setItems(ItemList.create(state.characters,charactersController))
        }
    }

    override fun initViews() {
        binding.charactersRv.adapter = easyAdapter
    }
}