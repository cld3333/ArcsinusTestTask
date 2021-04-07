package ru.surfstudio.standard.f_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.message.MessageController
import ru.surfstudio.standard.f_history.databinding.FragmentFeedBinding
import ru.surfstudio.standard.f_history.di.HistoryScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import javax.inject.Inject

internal class HistoryFragmentView : BaseMviFragmentView<HistoryState, HistoryEvent>(), CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<HistoryEvent>

    @Inject
    override lateinit var sh: HistoryScreenStateHolder

    @Inject
    lateinit var messageController: MessageController

    private val binding by viewBinding(FragmentFeedBinding::bind)

    override fun createConfigurator() = HistoryScreenConfigurator(arguments)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun render(state: HistoryState) {
        if (state.message.isNotEmpty()) {
            messageController.showToast(message = state.message)
        }
    }

    override fun initViews() {
        binding.feedBtn.clicks().emit(HistoryEvent.OpenDialog)
    }
}