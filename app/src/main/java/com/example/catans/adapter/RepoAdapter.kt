package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.example.catans.R
import com.example.catans.databinding.ViewItemBinding
import com.example.catans.model.Article
import kotlinx.coroutines.CoroutineScope

class RepoAdapter(val scope: CoroutineScope) : PagingDataAdapter<Article, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ViewItemBinding

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.source == newItem.source
            }
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_item, parent, false)
        return ItemViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Article? = getItem(position)
        if (!item?.urlToImage.isNullOrEmpty() && !item?.description.isNullOrEmpty()) {
            holder.bindItem(item)
            itemViewBinding.ivImage.load(item?.urlToImage)
        }
    }

}