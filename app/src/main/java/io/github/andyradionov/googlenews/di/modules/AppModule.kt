package io.github.andyradionov.googlenews.di.modules

import android.support.annotation.NonNull
import dagger.Module
import dagger.Provides
import io.github.andyradionov.googlenews.data.datasource.local.NewsDao
import io.github.andyradionov.googlenews.data.datasource.server.NewsApi
import io.github.andyradionov.googlenews.data.message.SystemMessageNotifier
import io.github.andyradionov.googlenews.data.repositories.NewsRepository
import io.github.andyradionov.googlenews.interactors.NewsInteractor
import io.github.andyradionov.googlenews.utils.RxComposers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */

@Module
class AppModule {

    @NonNull
    @Provides
    @Singleton
    fun provideNewsInteractor(repository: NewsRepository) = NewsInteractor(repository)

    @NonNull
    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao) = NewsRepository(newsApi, newsDao)

    @NonNull
    @Provides
    @Singleton
    fun provideRxComposers() = RxComposers(Schedulers.io(), AndroidSchedulers.mainThread())

    @NonNull
    @Provides
    @Singleton
    fun provideMessageNotifier() = SystemMessageNotifier()
}
