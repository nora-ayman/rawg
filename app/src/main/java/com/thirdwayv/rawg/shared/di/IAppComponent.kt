package com.thirdwayv.rawg.shared.di

import com.thirdwayv.rawg.features.MainActivity
import com.thirdwayv.rawg.shared.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    ActivityBuilder::class])
interface IAppComponent: AndroidInjector<Application> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): IAppComponent
    }

    override fun inject(application: Application)
}