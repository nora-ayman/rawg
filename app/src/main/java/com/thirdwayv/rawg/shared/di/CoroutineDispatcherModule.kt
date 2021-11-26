package com.thirdwayv.rawg.shared.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
class CoroutineDispatcherModule {

    @IoDispatcher
    @Provides
    fun providesCoroutineDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun providesCoroutineDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun providesCoroutineDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher