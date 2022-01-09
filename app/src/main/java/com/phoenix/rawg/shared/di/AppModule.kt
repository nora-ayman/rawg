package com.phoenix.rawg.shared.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
public class AppModule {

    @Provides
    @Singleton
    internal fun providesApplication(application: Application): Context = application
}