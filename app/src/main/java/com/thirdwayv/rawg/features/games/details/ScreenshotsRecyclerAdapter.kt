package com.thirdwayv.rawg.features.games.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.thirdwayv.rawg.databinding.ItemGameScreenshotBinding
import com.thirdwayv.rawg.shared.store.models.response.GameScreenshotResponse
import java.lang.ref.WeakReference
import java.util.concurrent.ThreadLocalRandom

class ScreenshotsRecyclerAdapter constructor(val items: MutableLiveData<List<GameScreenshotResponse>>,
                                             lifeCycleOwner: WeakReference<LifecycleOwner>,
                                             val context: Context

): RecyclerView.Adapter<ScreenshotsRecyclerAdapter.ViewHolder>() {


    init {
        lifeCycleOwner.get()?.let {
            items.observe(it) {
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameScreenshotBinding.inflate(layoutInflater, parent, false)
        val holder = ViewHolder(binding)
        setItemSize(parent, holder)
        return holder
    }

    private fun setItemSize(viewGroup: ViewGroup, holder: ViewHolder) {
        val displayWidth = context.resources.displayMetrics.widthPixels
        holder.itemView.layoutParams.width = displayWidth / 4
        viewGroup.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            holder.itemView.layoutParams.width = displayWidth / 4
            holder.itemView.requestLayout()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.value!![position])

    override fun getItemCount() = items.value.orEmpty().size

    inner class ViewHolder(private val binding: ItemGameScreenshotBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameScreenshotResponse) {
            binding.viewModel = item
        }
    }
}