package ru.surfstudio.standard.f_search.history

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class HistoryEvent : Event {

    object OpenDialog: HistoryEvent()
    data class ShowDialogResult(val message: String): HistoryEvent()

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, HistoryEvent()
    data class Lifecycle(override var stage: LifecycleStage) : HistoryEvent(), LifecycleEvent
}
