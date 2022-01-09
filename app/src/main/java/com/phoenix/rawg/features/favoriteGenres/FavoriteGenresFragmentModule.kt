package com.phoenix.rawg.features.favoriteGenres

import androidx.lifecycle.ViewModel
import com.phoenix.rawg.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteGenresFragmentModule {

    @ContributesAndroidInjector(modules = [])
    abstract fun bindsFragment(): FavoriteGenresFragment

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteGenresViewModel::class)
    abstract fun bindsViewModel(viewModel: FavoriteGenresViewModel): ViewModel
}