package io.github.andyradionov.googlenews.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.github.andyradionov.googlenews.data.entities.Article

/**
 * @author Andrey Radionov
 */
interface MainView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showBottomSheet(article: Article)

    @StateStrategyType(SkipStrategy::class)
    fun showMessage(msg: String)

    @StateStrategyType(SkipStrategy::class)
    fun showNotConnected()
}
