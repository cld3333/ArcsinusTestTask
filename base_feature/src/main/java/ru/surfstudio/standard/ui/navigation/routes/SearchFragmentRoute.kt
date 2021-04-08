package ru.surfstudio.standard.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabHeadRoute

class SearchFragmentRoute : FragmentRoute(), TabHeadRoute {

    override fun getScreenClassPath(): String =
            "ru.surfstudio.standard.f_characters.search.SearchFragmentView"
}