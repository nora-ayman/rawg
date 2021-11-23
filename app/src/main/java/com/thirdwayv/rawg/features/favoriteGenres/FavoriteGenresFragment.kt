package com.thirdwayv.rawg.features.favoriteGenres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thirdwayv.rawg.R
import com.thirdwayv.rawg.databinding.FragmentFavoriteGenresBinding
import dagger.android.support.DaggerFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class FavoriteGenresFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentFavoriteGenresBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteGenresBinding.inflate(inflater, container, false).apply {

            this.viewModel = ViewModelProvider(this@FavoriteGenresFragment, viewModelProviderFactory)[FavoriteGenresViewModel::class.java]
            lifecycleOwner = viewLifecycleOwner
        }
        setupGenresRv()
        return binding.root
    }

    private fun setupGenresRv() {
        binding.genresRv.apply {
            adapter = GenreRecyclerAdapter(binding.viewModel!!.genres,
                WeakReference(viewLifecycleOwner)) { item ->
                binding.viewModel!!.updateFavorites(item)
            }
            layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_list_divider_on_secondary)!!)
            addItemDecoration(dividerItemDecoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener()  {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) &&
                        binding.viewModel!!.genres.value.orEmpty().size < binding.viewModel!!.count.value!!)
                        binding.viewModel!!.loadGenres()
                }
            })
        }
    }

}