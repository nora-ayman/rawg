package com.thirdwayv.rawg.features.games

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.thirdwayv.rawg.databinding.ItemGameBinding
import com.thirdwayv.rawg.features.favoriteGenres.GenreRecyclerAdapter
import com.thirdwayv.rawg.shared.store.models.response.GameResponse
import com.thirdwayv.rawg.shared.utils.DisplayMetricsUtil
import java.lang.ref.WeakReference
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class GamesRecyclerAdapter constructor(val items: MutableLiveData<List<GameResponse>>,
                                       lifeCycleOwner: WeakReference<LifecycleOwner>,
                                       val context: Context,
                                       val displayMetricsUtil: DisplayMetricsUtil,
                                       val onItemClicked: (View, Int) -> Unit

): RecyclerView.Adapter<GamesRecyclerAdapter.ViewHolder>() {


    init {
        lifeCycleOwner.get()?.let {
            items.observe(it) {
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        val holder = ViewHolder(binding)
        setItemSize(parent, holder)
        return holder
    }

    private fun setItemSize(viewGroup: ViewGroup, holder: ViewHolder) {
        val randomHeight = displayMetricsUtil.getRandomHeight()
        holder.itemView.layoutParams.height = randomHeight
        viewGroup.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            holder.itemView.layoutParams.height = randomHeight
            holder.itemView.requestLayout()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.value!![position])

    override fun getItemCount() = items.value.orEmpty().size

    inner class ViewHolder(private val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameResponse) {
            binding.viewModel = item
            binding.root.setOnClickListener {
                onItemClicked.invoke(it, item.id)
            }
        }
    }
}