package com.thirdwayv.rawg.features.favoriteGenres

import androidx.lifecycle.ViewModel
import com.thirdwayv.rawg.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteGenresFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindsFragment(): FavoriteGenresFragment

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteGenresViewModel::class)
    abstract fun bindsViewModel(viewModel: FavoriteGenresViewModel): ViewModel
}