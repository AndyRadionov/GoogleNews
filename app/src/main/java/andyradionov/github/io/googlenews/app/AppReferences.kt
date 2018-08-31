package andyradionov.github.io.googlenews.app

import andyradionov.github.io.googlenews.BuildConfig

/**
 * @author Andrey Radionov
 */

const val COUNTRY_CODE = "ru"
const val PAGE_SIZE = 100
const val SORT_BY = "publishedAt";
const val BASE_URL = "https://newsapi.org/v2/"
const val TOP_NEWS_REQUEST = "top-headlines?country=$COUNTRY_CODE"
const val SEARCH_NEWS_REQUEST = "everything?sortBy=$SORT_BY&pageSize=$PAGE_SIZE"
