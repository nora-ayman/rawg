package com.phoenix.rawg.shared.di

import com.phoenix.rawg.features.MainActivity
import com.phoenix.rawg.features.favoriteGenres.FavoriteGenresFragmentModule
import com.phoenix.rawg.features.games.GamesFragmentModule
import com.phoenix.rawg.features.games.details.GamesDetailsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [
        GamesFragmentModule::class,
        FavoriteGenresFragmentModule::class,
        GamesDetailsFragmentModule::class])
    abstract fun bindsMainActivity(): MainActivity
}