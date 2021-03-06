package com.phoenix.rawg.features.games

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.phoenix.rawg.R
import com.phoenix.rawg.databinding.FragmentGamesBinding
import com.phoenix.rawg.shared.ui.StaggeredSpaceItemDecoration
import com.phoenix.rawg.shared.utils.DisplayMetricsUtil
import dagger.android.support.DaggerFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class GamesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var displayMetricsUtil: DisplayMetricsUtil

    lateinit var binding: FragmentGamesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);

        binding = FragmentGamesBinding.inflate(inflater, container, false).apply {

            this.viewModel = ViewModelProvider(this@GamesFragment, viewModelProviderFactory)[GamesViewModel::class.java]
            lifecycleOwner = viewLifecycleOwner
        }
        setupGamesRv()
        return binding.root
    }

    private fun setupGamesRv() {
        binding.gamesRv.apply {
            adapter = GamesRecyclerAdapter(binding.viewModel!!.filteredGames,
                WeakReference(viewLifecycleOwner),
                requireContext(),
                displayMetricsUtil
            ) { view, id ->
                findNavController().navigate(GamesFragmentDirections.actionGameDetails().setGameId(id))

            }

            val staggeredGridLayoutManager = StaggeredGridLayoutManager(
                if(displayMetricsUtil.isScreenWidthCompact())
                    2
                else
                    3,
                StaggeredGridLayoutManager.VERTICAL)
            staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            layoutManager = staggeredGridLayoutManager
            addItemDecoration(StaggeredSpaceItemDecoration())

            addOnScrollListener(object : RecyclerView.OnScrollListener()  {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) &&
                        binding.viewModel!!.filteredGames.value.orEmpty().size < binding.viewModel!!.count.value!! &&
                        !binding.viewModel!!.isSearchApplied.value!!)
                        binding.viewModel!!.loadGames()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_nav_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        observeSearchView(searchView)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun observeSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.viewModel?.searchGenresByName(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                binding.viewModel?.searchGenresByName(query)
                return true
            }
        })
    }
}