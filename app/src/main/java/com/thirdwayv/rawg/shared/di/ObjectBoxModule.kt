package com.thirdwayv.rawg.shared.di

import android.content.Context
import com.thirdwayv.rawg.BuildConfig
import com.thirdwayv.rawg.shared.store.models.objectbox.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ObjectBoxModule {
    @Singleton
    @Provides
    fun providesBoxStore(context: Context): BoxStore {
        val boxStore = MyObjectBox.builder()
            .androidContext(context)
            .build()
        if (BuildConfig.DEBUG)
            AndroidObjectBrowser(boxStore).start(context)
        return boxStore
    }
}