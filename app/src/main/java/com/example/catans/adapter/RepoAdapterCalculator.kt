package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.catans.R

class RepoAdapterCalculator(private val fragment: Fragment, private val list: Array<String>, private val itemClick: (index: Int) -> Unit ): BaseAdapter() {

    private var view: View? = null
    private var viewHolder: ViewHolder? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            view = LayoutInflater.from(fragment.context).inflate(R.layout.item_grid, null)
            viewHolder = ViewHolder()
            viewHolder?.card = view?.findViewById(R.id.cardGrid)
            viewHolder?.text = view?.findViewById(R.id.tvGrid)
            view?.tag = viewHolder
        } else {
            viewHolder = view?.tag as ViewHolder
        }
        val bgColor: Int
        val tvColor: Int
        when(position) {
            0 -> {
                bgColor = R.color.grey_200
                tvColor = R.color.red_200
            }
            1, 2, 3, 7, 11, 15 -> {
                bgColor = R.color.grey_200
                tvColor = R.color.deep_green
            }
            19 -> {
                bgColor = R.color.deep_green
                tvColor = R.color.white
            }
            else -> {
                bgColor = R.color.white
                tvColor = R.color.black
            }
        }
        viewHolder?.text?.text = list[position]
        viewHolder?.text?.setTextColor(ResourcesCompat.getColorStateList(fragment.resources, tvColor, fragment.activity?.theme))
        viewHolder?.text?.background = ResourcesCompat.getDrawable(fragment.resources, bgColor, fragment.activity?.theme)
        viewHolder?.card?.setOnClickListener {view ->
            itemClick(position)
        }
        return view!!
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    internal inner class ViewHolder {
        var card: CardView? = null
        var text: TextView? = null
    }
}
