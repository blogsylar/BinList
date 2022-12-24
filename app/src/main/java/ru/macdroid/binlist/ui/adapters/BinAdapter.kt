package ru.macdroid.binlist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.macdroid.binlist.R
import ru.macdroid.binlist.databinding.ListItemBinding
import ru.macdroid.binlist.model.db.DBModel

class BinAdapter(val listener: Listener?): ListAdapter<DBModel, BinAdapter.BinViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return BinViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: BinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BinViewHolder(view: View, val listener: Listener?) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        var itemTemp: DBModel? = null

        init {
            itemView.setOnClickListener {
                itemTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }

        fun bind(item: DBModel) {
            itemTemp = item
            binding.tvBinItem.text = item.bin
        }
    }


    class Comparator : DiffUtil.ItemCallback<DBModel>() {
        override fun areItemsTheSame(oldItem: DBModel, newItem: DBModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: DBModel, newItem: DBModel): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClick(item: DBModel)
    }
}