package ru.surfstudio.standard.f_history.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.mvi.impls.event.hub.dependency.ScreenEventHubDependency
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinder
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinderDependency
import ru.surfstudio.android.core.mvp.configurator.BindableScreenComponent
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_history.*
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.FragmentScreenConfigurator
import ru.surfstudio.standard.ui.navigation.routes.HistoryFragmentRoute
import ru.surfstudio.standard.ui.screen_modules.CustomScreenModule
import ru.surfstudio.standard.ui.screen_modules.FragmentScreenModule

/**
 * Конфигуратор таба новости [HistoryFragmentView]
 */
internal class HistoryScreenConfigurator(arguments: Bundle?) : FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [FragmentScreenModule::class, HistoryScreenModule::class]
    )
    internal interface HistoryScreenComponent : BindableScreenComponent<HistoryFragmentView>

    @Module
    internal class HistoryScreenModule(route: HistoryFragmentRoute) : CustomScreenModule<HistoryFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
                screenEventHubDependency: ScreenEventHubDependency
        )= ScreenEventHub<HistoryEvent>(screenEventHubDependency, HistoryEvent::Lifecycle)

        @Provides
        @PerScreen
        fun provideBinder(
          screenBinderDependency: ScreenBinderDependency,
          eventHub: ScreenEventHub<HistoryEvent>,
          mw: HistoryMiddleware,
          sh: HistoryScreenStateHolder,
          reducer: HistoryReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
            parentComponent: ActivityComponent?,
            fragmentScreenModule: FragmentScreenModule?,
            args: Bundle?
    ): ScreenComponent<*> {
        return DaggerHistoryScreenConfigurator_HistoryScreenComponent.builder()
                .activityComponent(parentComponent)
                .fragmentScreenModule(fragmentScreenModule)
                .historyScreenModule(HistoryScreenModule(HistoryFragmentRoute()))
                .build()
    }
}
