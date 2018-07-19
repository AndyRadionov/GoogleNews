package andyradionov.github.io.googlenews.data

import andyradionov.github.io.googlenews.app.App
import andyradionov.github.io.googlenews.news.NewsContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
class NewsStore {

    @Inject lateinit var mNewsApi: NewsApi

    init {
        App.appComponent.inject(this)
    }

    private var mSubscription: Disposable? = null
    private var cacheQuery: String = ""
    private var cache: List<Article> = emptyList()

    fun fetchNews(query: String = "", callback: NewsCallback) {

        unsubscribe()

        if (isCached(query)) {
            callback.onSuccessLoading(cache)
            return
        }

        val newsObservable =
                if (query.isEmpty()) {
                    mNewsApi.getTopNews()
                } else {
                    mNewsApi.searchNews(query)
                }

        newsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ callback.onErrorLoading() })
                .map { it.articles }
                .subscribe({
                    if (it.isEmpty()) {
                        callback.onErrorLoading()
                    } else {
                        cacheQuery = query
                        cache = it
                        callback.onSuccessLoading(it)
                    }
                }, { callback.onErrorLoading() })
    }

    public fun unsubscribe() {
        if (mSubscription?.isDisposed == true) {
            mSubscription?.dispose();
            mSubscription = null;
        }
    }

    private fun isCached(query: String): Boolean {
        return query == cacheQuery && !cache.isEmpty()
    }
}