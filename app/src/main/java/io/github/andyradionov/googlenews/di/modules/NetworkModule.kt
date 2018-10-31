package io.github.andyradionov.googlenews.di.modules

import android.support.annotation.NonNull
import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.github.andyradionov.googlenews.BuildConfig
import io.github.andyradionov.googlenews.app.NewsApp
import io.github.andyradionov.googlenews.data.NewsApi
import io.github.andyradionov.googlenews.utils.isInternetAvailable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class NetworkModule(private val newsApp: NewsApp) {

    @NonNull
    @Provides
    @Singleton
    fun provideNewsApi(httpClient: OkHttpClient): NewsApi {

        return Retrofit.Builder()
                .baseUrl(BuildConfig.ApiUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(NewsApi::class.java)
    }

    @NonNull
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(initApiKeyInterceptor())
                .addInterceptor(initOfflineCacheInterceptor())
                .addNetworkInterceptor(initCacheInterceptor())
                .cache(initCache())
                .build()
    }

    private fun initApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder().url(
                    original.url()
                            .newBuilder()
                            .addQueryParameter("apiKey", BuildConfig.ApiKey)
                            .build())
                    .build()

            chain.proceed(request)
        }
    }

    private fun initCache(): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(newsApp.cacheDir, "http-cache"),
                    (10 * 1024 * 1024).toLong()) // 10 MB
        } catch (e: Exception) {
            Log.e(TAG, "Could not create Cache!")
        }

        return cache
    }

    private fun initCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            // re-write response header to force use of cache
            val cacheControl = CacheControl.Builder()
                    .maxAge(10, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .removeHeader(PRAGMA_HEADER)
                    .removeHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER)
                    .removeHeader(VARY_HEADER)
                    .removeHeader(CACHE_CONTROL_HEADER)
                    .header(CACHE_CONTROL_HEADER, cacheControl.toString())
                    .build()
        }
    }

    private fun initOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if (!isInternetAvailable(newsApp)) {
                val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build()
            }

            chain.proceed(request)
        }
    }

    companion object {
        private const val TAG = "AppModule"
        private val CACHE_CONTROL_HEADER = "Cache-Control"
        private val PRAGMA_HEADER = "Pragma"
        private val ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin"
        private val VARY_HEADER = "Vary"
    }
}