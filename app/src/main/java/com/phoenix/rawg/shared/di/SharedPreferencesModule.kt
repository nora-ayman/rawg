package com.phoenix.rawg.shared.di

import android.app.Application
import android.content.Context
import com.phoenix.rawg.shared.constants.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun providesSharedPreferences(application: Application) = application.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

}