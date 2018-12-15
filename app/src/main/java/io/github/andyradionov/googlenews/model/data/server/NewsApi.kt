package io.github.andyradionov.googlenews.model.data.server

import io.github.andyradionov.googlenews.app.SEARCH_NEWS_REQUEST
import io.github.andyradionov.googlenews.app.TOP_NEWS_REQUEST
import io.github.andyradionov.googlenews.entities.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Andrey Radionov
 */
interface NewsApi {

    @GET(TOP_NEWS_REQUEST)
    fun getTopNews(): Observable<NewsResponse>

    @GET(TOP_NEWS_REQUEST)
    fun getHeadlinesNews(@Query("category") category: String): Observable<NewsResponse>

    @GET(SEARCH_NEWS_REQUEST)
    fun searchNews(@Query("q") query: String): Observable<NewsResponse>

}