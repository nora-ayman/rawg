package com.thirdwayv.rawg.shared.di

import com.thirdwayv.rawg.features.MainActivity
import com.thirdwayv.rawg.features.favoriteGenres.FavoriteGenresFragmentModule
import com.thirdwayv.rawg.features.games.GamesFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [
        GamesFragmentModule::class,
        FavoriteGenresFragmentModule::class])
    abstract fun bindsMainActivity(): MainActivity
}