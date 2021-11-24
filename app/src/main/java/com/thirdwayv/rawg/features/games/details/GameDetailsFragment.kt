package com.thirdwayv.rawg.features.games.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.thirdwayv.rawg.databinding.FragmentGameDetailsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GameDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var helper: GameDetailsHelper

    private lateinit var binding: FragmentGameDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navigationArguments: GameDetailsFragmentArgs by navArgs()

        binding = FragmentGameDetailsBinding.inflate(inflater, container, false).apply {

            this.viewModel = ViewModelProvider(this@GameDetailsFragment, viewModelProviderFactory)[GameDetailsViewModel::class.java].apply {
                this.gameId = navigationArguments.gameId
            }
            helper = this@GameDetailsFragment.helper
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

}