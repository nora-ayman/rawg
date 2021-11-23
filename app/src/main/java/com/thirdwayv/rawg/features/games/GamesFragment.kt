package com.thirdwayv.rawg.features.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thirdwayv.rawg.databinding.FragmentGamesBinding
import dagger.android.support.DaggerFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class GamesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentGamesBinding
    var pastVisibleItems = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
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
                WeakReference(viewLifecycleOwner)
            ) {
                //todo
            }
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

            addOnScrollListener(object : RecyclerView.OnScrollListener()  {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
////                    super.onScrolled(recyclerView, dx, dy)
//                    visibleItemCount = (layoutManager as GridLayoutManager).childCount;
//                    totalItemCount = (layoutManager as GridLayoutManager).itemCount;
//                    pastVisibleItems = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount &&
//                        totalItemCount < binding.viewModel!!.count.value!!)
//                        binding.viewModel!!.loadGames()
//                    else
//                        recyclerView.removeOnScrollListener(this)
//                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1))
                        binding.viewModel!!.loadGames()
                }
            })
        }
    }
}