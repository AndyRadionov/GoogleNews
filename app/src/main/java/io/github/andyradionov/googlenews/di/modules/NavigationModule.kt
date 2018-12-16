package io.github.andyradionov.googlenews.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class NavigationModule {

    private val cicerone = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter() = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder() = cicerone.navigatorHolder
}
