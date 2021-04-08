package ru.surfstudio.standard.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabHeadRoute

class HistoryFragmentRoute : FragmentRoute(), TabHeadRoute {

    override fun getScreenClassPath(): String =
            "ru.surfstudio.standard.f_characters.history.HistoryFragmentView"
}