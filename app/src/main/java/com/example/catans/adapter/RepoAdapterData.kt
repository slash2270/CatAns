package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.catans.R
import com.example.catans.databinding.ItemDataBinding
import com.example.catans.model.DataChild

class RepoAdapterData(private val list: List<DataChild>, private val fragment: Fragment) : RecyclerView.Adapter<ItemViewHolder>() {

    private lateinit var binding: ItemDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_data, parent, false)
        return ItemViewHolder(binding, fragment)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.bindItemData(item)
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.size
    }

}