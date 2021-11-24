package com.thirdwayv.rawg.features.games.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thirdwayv.rawg.databinding.FragmentGameDetailsBinding
import com.thirdwayv.rawg.shared.constants.Constants
import dagger.android.support.DaggerFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class GameDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var helper: GameDetailsHelper

    lateinit var mediaController: MediaController

    private lateinit var binding: FragmentGameDetailsBinding

    private var currentPlayerPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navigationArguments: GameDetailsFragmentArgs by navArgs()

        binding = FragmentGameDetailsBinding.inflate(inflater, container, false).apply {
            mediaController = MediaController(requireContext())
            this.viewModel = ViewModelProvider(this@GameDetailsFragment, viewModelProviderFactory)[GameDetailsViewModel::class.java].apply {
                this.gameId = navigationArguments.gameId
            }
            helper = this@GameDetailsFragment.helper
            lifecycleOwner = viewLifecycleOwner
        }
        if (savedInstanceState != null) {
            currentPlayerPosition = savedInstanceState.getInt(Constants.PLAYER_POSITION);
        }
        observeTrailerData()
        setupScreenshotsRv()
        return binding.root
    }

    private fun setupScreenshotsRv() {
        binding.screenshotsRv.apply {
            adapter = ScreenshotsRecyclerAdapter(binding.viewModel!!.screenshots,
                WeakReference(viewLifecycleOwner),
                requireContext()
            )
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun observeTrailerData() {
        if (!binding.viewModel!!.trailer.hasObservers())
            binding.viewModel!!.trailer.observe(viewLifecycleOwner) {
                it?.let {
                    it.video?.let {
                        setVideoPlayer(it.videoUrl)
                    }
                }
            }
    }

    private fun setVideoPlayer(videoUrl: String) {

        binding.videoVv.apply {
            mediaController.setAnchorView(this)
            setVideoPath(videoUrl)
            setMediaController(mediaController)
            setOnPreparedListener {
                binding.viewModel!!.setTrailerVideoStatus(true)
                if (currentPlayerPosition > 0 )
                    seekTo(currentPosition, true)
                else
                    seekTo(100, true)
                requestFocus()
                it.start()
            }

            setOnCompletionListener {
                seekTo(0, false)
            }

            setOnErrorListener { _, _, _ ->
                seekTo(0, false)
                false
            }
        }
    }

    private fun seekTo(position: Int, isPlaying: Boolean) {
        binding.viewModel!!.setTrailerVideoStatus(isPlaying)
        binding.videoVv.seekTo(position)
        if (isPlaying)
            currentPlayerPosition = 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.PLAYER_POSITION, binding.videoVv.currentPosition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.videoVv.stopPlayback()
    }

}