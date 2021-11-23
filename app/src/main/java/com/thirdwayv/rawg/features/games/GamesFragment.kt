package com.thirdwayv.rawg.features.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.thirdwayv.rawg.databinding.FragmentGamesBinding
import com.thirdwayv.rawg.shared.ui.StaggeredSpaceItemDecoration
import dagger.android.support.DaggerFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class GamesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentGamesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(inflater, container, false).apply {

            this.viewModel = ViewModelProvider(this@GamesFragment, viewModelProviderFactory)[GamesViewModel::class.java]
            lifecycleOwner = viewLifecycleOwner
        }
        setupGamesRv()
        return binding.root
    }

    private fun setupGamesRv() {
        binding.gamesRv.apply {
            adapter = GamesRecyclerAdapter(binding.viewModel!!.games,
                WeakReference(viewLifecycleOwner),
                requireContext()
            ) {
                //todo
            }

            val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            layoutManager = staggeredGridLayoutManager
            addItemDecoration(StaggeredSpaceItemDecoration(16))

            addOnScrollListener(object : RecyclerView.OnScrollListener()  {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1))
                        binding.viewModel!!.loadGames()
                }
            })
        }
    }
}