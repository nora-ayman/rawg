package com.thirdwayv.rawg.features.favoriteGenres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thirdwayv.rawg.databinding.ItemGenreBinding
import com.thirdwayv.rawg.shared.utils.ListDiffUtilCallback
import java.lang.ref.WeakReference

class GenreRecyclerAdapter constructor(val items: MutableLiveData<List<GenreItemViewModel>>,
                                       lifeCycleOwner: WeakReference<LifecycleOwner>,
                                       val onFavoriteCheckChanged: (GenreItemViewModel) -> Unit
): RecyclerView.Adapter<GenreRecyclerAdapter.ViewHolder>() {

    private var lastItems = emptyList<GenreItemViewModel>()

    init {
        lifeCycleOwner.get()?.let {
            items.observe(it) {
                val listDiffUtilCallback = ListDiffUtilCallback(lastItems, it)
                val diffResult = DiffUtil.calculateDiff(listDiffUtilCallback)
                diffResult.dispatchUpdatesTo(this)
                lastItems = it
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(layoutInflater, parent, false)
        val holder = ViewHolder(binding)
        setItemSize(parent, holder)
        return holder
    }

    private fun setItemSize(viewGroup: ViewGroup, holder: GenreRecyclerAdapter.ViewHolder) {
        holder.itemView.layoutParams.height = viewGroup.height / 5
        viewGroup.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            holder.itemView.layoutParams.height = viewGroup.height / 5
            holder.itemView.requestLayout()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.value!![position])

    override fun getItemCount() = items.value.orEmpty().size

    inner class ViewHolder(private val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GenreItemViewModel) {
            binding.viewModel = item
//            binding.favoriteCb.setOnCheckedChangeListener(null)
            binding.favoriteCb.setOnClickListener {
                item.favorite = !item.favorite
                onFavoriteCheckChanged.invoke(item)
            }
        }
    }
}