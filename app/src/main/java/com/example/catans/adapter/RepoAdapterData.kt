package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catans.BR
import com.example.catans.R
import com.example.catans.databinding.ItemAirportBinding
import com.example.catans.databinding.ItemDataBinding
import com.example.catans.model.Airport
import com.example.catans.model.DataChild

class RepoAdapterData(private val fragment: Fragment) : PagingDataAdapter<DataChild, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ItemDataBinding
    private var listClick = arrayListOf<Boolean>()
    private val list = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DataChild>() {
            override fun areItemsTheSame(oldItem: DataChild, newItem: DataChild): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataChild, newItem: DataChild): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        list.forEach { _ ->
            listClick.add(false)
        }
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_data, parent, false)
        return ItemViewHolder(itemViewBinding, fragment)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        if (isClick) R.color.purple_200 else R.color.grey_500
        val item: DataChild? = getItem(position)
        val llRoot = holder.itemView.rootView.findViewById<LinearLayout>(R.id.ll_root)
        val tvCurrencyCode = holder.itemView.rootView.findViewById<TextView>(R.id.tvCurrencyCode)
        val tvCurrencyMoney= holder.itemView.rootView.findViewById<TextView>(R.id.tvCurrencyMoney)
        llRoot.tag = holder.absoluteAdapterPosition
        llRoot.setOnClickListener {
            val p = it.tag as Int
            listClick[p] = !listClick[p]
            llRoot.background = ResourcesCompat.getDrawable(fragment.resources, if (listClick[p]) R.color.grey_500 else R.color.purple_100, fragment.activity?.theme)
            tvCurrencyCode.setTextColor(ResourcesCompat.getColor(fragment.resources, if (listClick[p]) R.color.purple_200 else R.color.grey_500, fragment.activity?.theme))
            tvCurrencyMoney.setTextColor(ResourcesCompat.getColor(fragment.resources, if (listClick[p]) R.color.purple_200 else R.color.grey_500, fragment.activity?.theme))
        }
        holder.bindItemData(item)
    }

}


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        list.forEach { _ ->
//            listClick.add(false)
//        }
//        binding = DataBindingUtil.inflate(LayoutInflater.from(fragment.context), R.layout.item_data, parent, false)
//        return ItemViewHolder(binding, fragment)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = list[position]
//        holder.bindItemData(item, binding)
//        val index = holder.absoluteAdapterPosition
//        holder.itemView.tag = index
//        binding.llRoot.tag = index
//        binding.tvCurrencyCode.tag = index
//        binding.tvCurrencyMoney.tag = index
//        holder.itemView.setOnClickListener {
//            val p = it.tag as Int
//            listClick[p] = !listClick[p]
//            // itemViewBinding.llRoot.background = ResourcesCompat.getDrawable(fragment.resources, if (isClick) R.color.grey_500 else R.color.purple_100, fragment.activity?.theme)
//            binding.tvCurrencyCode.setTextColor(ResourcesCompat.getColor(fragment.resources, if (listClick[p]) R.color.purple_200 else R.color.grey_500, fragment.activity?.theme))
//            binding.tvCurrencyMoney.setTextColor(ResourcesCompat.getColor(fragment.resources, if (listClick[p]) R.color.purple_200 else R.color.grey_500, fragment.activity?.theme))
//            notifyDataSetChanged()
//        }
//    }