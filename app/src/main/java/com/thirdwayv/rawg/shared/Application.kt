package com.thirdwayv.rawg.shared

import com.thirdwayv.rawg.shared.di.DaggerIAppComponent
import dagger.android.DaggerApplication

class Application : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector() = DaggerIAppComponent.builder()
        .application(this)
        .build()

}

