package com.thirdwayv.rawg.shared.di

import com.thirdwayv.rawg.shared.constants.Constants.API_KEY
import com.thirdwayv.rawg.shared.constants.Constants.BASE_URL
import com.thirdwayv.rawg.shared.constants.Constants.HEADER_API_KEY
import com.thirdwayv.rawg.shared.store.api.IRawgService
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient
            .Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val request = chain.request()
                    val url = request
                        .url
                        .newBuilder()
                        .addQueryParameter(HEADER_API_KEY, API_KEY)
                        .build()

                    return@Interceptor chain.proceed(request.newBuilder().url(url).build())
                }).build()

    @Singleton
    @Provides
    fun provideRawgService(retrofit: Retrofit) =
        retrofit.create(IRawgService::class.java)
}