package com.example.catans.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
class RepoAdapterData(private val fragment: Fragment, private val liveData: MutableLiveData<ArrayList<DataChild>>, private val bottomSheetClick: () -> Unit) : PagingDataAdapter<DataChild, ItemViewHolder>(
    COMPARATOR
) {

    private lateinit var itemViewBinding: ItemDataBinding
    private var listClick = arrayListOf<Boolean>()
    private var index = MutableLiveData<Int>()

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
        if (listClick.isEmpty() && liveData.value?.isNotEmpty() == true) {
            liveData.value?.forEach { _ ->
                listClick.add(false)
            }
        }
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
        tvCurrencyCode.text = item?.code
        tvCurrencyMoney.text = item?.money
        llRoot.setOnClickListener {
            bottomSheetClick()
            val new = holder.absoluteAdapterPosition
            if (listClick.isNotEmpty()) {
                Log.d("RepoAdapterData old", index.value.toString())
                Log.d("RepoAdapterData new", new.toString())
                index.observe(fragment) { old ->
                    if (old != null && old != new) {
                        listClick[old] = false
                        for (i in liveData.value?.indices!!) {
                            listClick[i] = false
                            itemClick(old, llRoot, tvCurrencyCode, tvCurrencyMoney)
                        }
                    }
                }
                listClick[new] = !listClick[new]
                itemClick(new, llRoot, tvCurrencyCode, tvCurrencyMoney)
                index.value = new
            }
        }
    }

    private fun itemClick(index: Int, llRoot: LinearLayout, tvCurrencyCode: TextView, tvCurrencyMoney: TextView) {
        llRoot.background = ResourcesCompat.getDrawable(fragment.resources, if (listClick[index]) R.color.grey_500 else R.color.purple_100, fragment.activity?.theme)
        tvCurrencyCode.setTextColor(ResourcesCompat.getColor(fragment.resources, if (listClick[index]) R.color.purple_200 else R.color.grey_500, fragment.activity?.theme))
        tvCurrencyMoney.setTextColor(ResourcesCompat.getColor(fragment.resources, if (listClick[index]) R.color.purple_200 else R.color.grey_500, fragment.activity?.theme))
    }

}