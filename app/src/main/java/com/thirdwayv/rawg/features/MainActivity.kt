package com.thirdwayv.rawg.features

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thirdwayv.rawg.R
import com.thirdwayv.rawg.databinding.ActivityMainBinding
import com.thirdwayv.rawg.shared.constants.Constants
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_games, R.id.navigation_favorite_genres
        ))
        if (sharedPreferences.getBoolean(Constants.IS_INITIAL_INSTALLATION, true)) {
            navController.navigate(R.id.navigation_favorite_genres)
            sharedPreferences.edit().putBoolean(Constants.IS_INITIAL_INSTALLATION, false).apply()
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}