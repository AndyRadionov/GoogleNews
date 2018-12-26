package io.github.andyradionov.topnews.ui.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Andrey Radionov
 */
@StateStrategyType(SkipStrategy::class)
interface DetailsView : MvpView {

    fun showToast(msgId: Int)
    fun onArticleFavourite(msgId: Int)
}