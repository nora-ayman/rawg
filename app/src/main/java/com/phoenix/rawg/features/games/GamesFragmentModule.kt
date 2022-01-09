package com.phoenix.rawg.features.games

import androidx.lifecycle.ViewModel
import com.phoenix.rawg.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class GamesFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindsFragment(): GamesFragment

    @Binds
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    abstract fun bindsViewModel(viewModel: GamesViewModel): ViewModel
}