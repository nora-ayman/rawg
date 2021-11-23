package com.thirdwayv.rawg.shared.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.thirdwayv.rawg.BuildConfig
import com.thirdwayv.rawg.shared.constants.Constants
import com.thirdwayv.rawg.shared.store.models.objectbox.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun providesSharedPreferences(application: Application) = application.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

}