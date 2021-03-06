package io.github.andyradionov.topnews.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.github.andyradionov.topnews.data.entities.Article
import io.github.andyradionov.topnews.data.message.SystemMessageNotifier
import io.github.andyradionov.topnews.data.message.SystemMessageType
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class MainPresenter @Inject constructor(
        private val router: Router,
        private val systemMessageNotifier: SystemMessageNotifier
) : MvpPresenter<MainView>() {

    private var notifierDisposable: Disposable? = null

    fun selectTab(screen: Screen) {
        router.navigateTo(screen)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscribeOnSystemMessages()
        viewState.initBottomTab()
    }

    override fun onDestroy() {
        notifierDisposable?.dispose()
    }

    private fun subscribeOnSystemMessages() {
        notifierDisposable = systemMessageNotifier.notifier
                .subscribe { msg ->
                    when (msg.type) {
                        SystemMessageType.ALERT -> viewState.showMessage(msg.data as Int)
                        SystemMessageType.BOTTOM -> viewState.showBottomSheet(msg.data as Article)
                        SystemMessageType.NO_CONNECTION -> viewState.showNotConnected()
                    }
                }
    }
}
