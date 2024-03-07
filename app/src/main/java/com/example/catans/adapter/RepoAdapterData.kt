package com.example.catans.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.catans.BR
import com.example.catans.R
import com.example.catans.databinding.ItemDataBinding
import com.example.catans.model.DataChild
import com.example.catans.util.Utils

class RepoAdapterData(private val fragment: Fragment, private val bottomSheetClick: () -> Unit) : PagingDataAdapter<DataChild, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ItemDataBinding
    private var index = MutableLiveData<Int>()

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DataChild>() {
            override fun areItemsTheSame(oldItem: DataChild, newItem: DataChild): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: DataChild, newItem: DataChild): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(fragment.context), R.layout.item_data, parent, false)
        return ItemViewHolder(itemViewBinding, fragment)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: DataChild? = getItem(position)
        itemViewBinding.setVariable(BR.item, item)
        itemViewBinding.executePendingBindings()
        val llRoot = holder.itemView.rootView.findViewById<LinearLayout>(R.id.ll_root)
        val tvCurrencyCode = holder.itemView.rootView.findViewById<TextView>(R.id.tvCurrencyCode)
        val tvCurrencyMoney= holder.itemView.rootView.findViewById<TextView>(R.id.tvCurrencyMoney)
        llRoot.setOnClickListener {
            fragment.activity?.let { activity ->
                Utils.closeKeyboard(activity, activity.window.decorView)
            }
            bottomSheetClick()
            val new = holder.absoluteAdapterPosition
            Log.d("RepoAdapterData old", index.value.toString())
            Log.d("RepoAdapterData new", new.toString())
            index.observe(fragment) { old ->
                if (old != null && old != new) {
                    itemClick(llRoot, tvCurrencyCode, tvCurrencyMoney, listOf(R.color.purple_100, R.color.grey_500, R.color.grey_500))
                }
            }
            itemClick(llRoot, tvCurrencyCode, tvCurrencyMoney, listOf(R.color.grey_500, R.color.purple_200,R.color.purple_200))
            index.value = new
        }
    }

    private fun itemClick(llRoot: LinearLayout, tvCurrencyCode: TextView, tvCurrencyMoney: TextView, listColor: List<Int>) {
        llRoot.background = ResourcesCompat.getDrawable(fragment.resources, listColor[0], fragment.activity?.theme)
        tvCurrencyCode.setTextColor(ResourcesCompat.getColor(fragment.resources, listColor[1], fragment.activity?.theme))
        tvCurrencyMoney.setTextColor(ResourcesCompat.getColor(fragment.resources, listColor[2], fragment.activity?.theme))
    }

}