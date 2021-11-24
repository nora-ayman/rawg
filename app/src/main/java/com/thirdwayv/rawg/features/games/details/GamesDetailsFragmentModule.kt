package com.thirdwayv.rawg.features.games.details

import android.content.Context
import android.widget.MediaController
import androidx.lifecycle.ViewModel
import com.thirdwayv.rawg.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class GamesDetailsFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindsFragment(): GameDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(GameDetailsViewModel::class)
    abstract fun bindsViewModel(viewModel: GameDetailsViewModel): ViewModel

    @Module
    abstract inner class SubModule {
        @Singleton
        @Provides
        fun providesMediaController(context: Context) = MediaController(context)

    }
}