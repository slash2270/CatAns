package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.catans.R
import com.example.catans.databinding.ViewItemBinding
import com.example.catans.model.Airport

class RepoAdapter : PagingDataAdapter<Airport, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ViewItemBinding

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Airport>() {
            override fun areItemsTheSame(oldItem: Airport, newItem: Airport): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Airport, newItem: Airport): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_view, parent, false)
        return ItemViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Airport? = getItem(position)
        holder.bindItem(item)
    }

}