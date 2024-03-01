package com.example.catans.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.example.catans.R

class RepoAdapterCalculator(private val fragment: Fragment, private val list: Array<String>) : BaseAdapter() {

    val name: ObservableField<String> = ObservableField("")
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
        name.set(list[position])
        val bgColor = when(position) {
            0, 1, 2, 3, 7, 11, 15 -> R.color.grey_300
            19 -> R.color.deep_green
            else -> R.color.white
        }
        viewHolder?.text?.text = list[position]
        viewHolder?.text?.background = ResourcesCompat.getDrawable(fragment.resources, bgColor, fragment.activity?.theme)
        viewHolder?.card?.setOnClickListener {
            when(position) {
                0 -> {}
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
                6 -> {}
                7 -> {}
                8 -> {}
                9 -> {}
                10 -> {}
                11 -> {}
                12 -> {}
                13 -> {}
                14 -> {}
                15 -> {}
                16 -> {}
                17 -> {}
                18 -> {}
                19 -> {}
            }
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
