package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.catans.R
import com.example.catans.databinding.ItemAirportBinding
import com.example.catans.model.Airport

class RepoAdapterAirport(val fragment: Fragment) : PagingDataAdapter<Airport, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ItemAirportBinding

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
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_airport, parent, false)
        return ItemViewHolder(itemViewBinding, fragment)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Airport? = getItem(position)
        holder.bindItemAirport(item)
    }

}