package com.thirdwayv.rawg.features.games.details

import androidx.lifecycle.ViewModel
import com.thirdwayv.rawg.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class GamesDetailsFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindsFragment(): GameDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(GameDetailsViewModel::class)
    abstract fun bindsViewModel(viewModel: GameDetailsViewModel): ViewModel
}