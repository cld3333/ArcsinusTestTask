package ru.surfstudio.standard.f_characters.history

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class HistoryEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, HistoryEvent()
    data class Lifecycle(override var stage: LifecycleStage) : HistoryEvent(), LifecycleEvent

    data class ShowCharacters(val characters: List<Character>): HistoryEvent()
}
