package ru.surfstudio.standard.f_search

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class SearchEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, SearchEvent()
    data class Lifecycle(override var stage: LifecycleStage) : SearchEvent(), LifecycleEvent


    sealed class Input : SearchEvent() {
        object BtnClick : Input()
    }

    data class GetCharactersRequestEvent(
            override val request: Request<List<Character>> = Request.Loading(),
    ) : RequestEvent<List<Character>>, SearchEvent()
}
