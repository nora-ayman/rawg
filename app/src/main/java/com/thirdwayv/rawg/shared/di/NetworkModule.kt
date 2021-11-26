package com.thirdwayv.rawg.shared.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.thirdwayv.rawg.shared.constants.Constants.API_KEY
import com.thirdwayv.rawg.shared.constants.Constants.BASE_URL
import com.thirdwayv.rawg.shared.constants.Constants.HEADER_API_KEY
import com.thirdwayv.rawg.shared.store.api.IRawgService
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context,
                            httpLoggingInterceptor: HttpLoggingInterceptor,
                            @Named("offlineInterceptor") offlineInterceptor: Interceptor,
                            @Named("onlineInterceptor") onlineInterceptor: Interceptor) =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache( Cache(context.cacheDir, (10 * 1024 * 1024).toLong()))
            .build()

    @Singleton
    @Provides
    fun provideRawgService(retrofit: Retrofit) =
        retrofit.create(IRawgService::class.java)

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor() =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    @Named("onlineInterceptor")
    fun provideOnlineInterceptor() =
        Interceptor { chain ->
            var request = chain.request()
            val url = request
                .url
                .newBuilder()
                .addQueryParameter(HEADER_API_KEY, API_KEY)
                .build()

            request =  request
                .newBuilder()
                .url(url)
                .cacheControl(CacheControl.Builder()
                    .maxAge(5, TimeUnit.MINUTES)
                    .build())
                .removeHeader("Pragma")
                .build()
            return@Interceptor chain.proceed(request)
        }

    @Singleton
    @Provides
    @Named("offlineInterceptor")
    fun provideOfflineInterceptor(context: Context) = Interceptor { chain ->
        var request = chain.request()
        val url = request
            .url
            .newBuilder()
            .addQueryParameter(HEADER_API_KEY, API_KEY)
            .build()

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (!isConnected)
            request = request
                .newBuilder()
                .url(url)
                .cacheControl(
                    CacheControl.Builder()
                        .maxStale(5, TimeUnit.MINUTES)
                        .build())
                .removeHeader("Pragma")
                .build()

        return@Interceptor chain.proceed(request)
    }
}