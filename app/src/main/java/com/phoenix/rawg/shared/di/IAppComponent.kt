package com.phoenix.rawg.shared.di

import com.phoenix.rawg.shared.Application
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
    CoroutineDispatcherModule::class,
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