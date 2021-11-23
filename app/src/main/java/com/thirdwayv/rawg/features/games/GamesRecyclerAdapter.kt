package com.thirdwayv.rawg.features.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.thirdwayv.rawg.databinding.ItemGameBinding
import com.thirdwayv.rawg.shared.store.models.response.GameResponse
import java.lang.ref.WeakReference

class GamesRecyclerAdapter constructor(val items: MutableLiveData<List<GameResponse>>,
                                       lifeCycleOwner: WeakReference<LifecycleOwner>,
                                       val onItemClicked: (GameResponse) -> Unit
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
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.value!![position])

    override fun getItemCount() = items.value.orEmpty().size

    inner class ViewHolder(private val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameResponse) {
            binding.viewModel = item
            binding.root.setOnClickListener {
                onItemClicked.invoke(item)
            }
        }
    }
}