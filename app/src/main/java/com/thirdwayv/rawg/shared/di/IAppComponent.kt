package com.thirdwayv.rawg.shared.di

import com.thirdwayv.rawg.shared.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelModule::class,
    AppModule::class,
    SharedPreferencesModule::class,
    NetworkModule::class,
    ObjectBoxModule::class,
    ActivityBuilder::class])
interface IAppComponent: AndroidInjector<Application> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: android.app.Application): Builder

        fun build(): IAppComponent
    }

    override fun inject(application: Application)
}