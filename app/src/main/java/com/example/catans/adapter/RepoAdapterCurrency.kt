package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.catans.R
import com.example.catans.databinding.ItemCurrencyBinding
import com.example.catans.model.Currency

class RepoAdapterCurrency(val fragment: Fragment) : PagingDataAdapter<Currency, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ItemCurrencyBinding

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_currency, parent, false)
        return ItemViewHolder(itemViewBinding, fragment)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Currency? = getItem(position)
        holder.bindItemCurrency(item)
    }

}