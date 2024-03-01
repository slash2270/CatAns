package com.example.catans.adapter

import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.catans.BR
import com.example.catans.R
import com.example.catans.databinding.ItemAirportBinding
import com.example.catans.model.Airport

class ItemViewHolder(private val viewDataBinding: ViewDataBinding, private val fragment: Fragment) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bindItemAirport(airport: Airport?) {
        val itemViewBinding: ItemAirportBinding = viewDataBinding as ItemAirportBinding
        airport?.terminal = when {
            airport?.terminal?.isEmpty() == true -> fragment.getString(R.string.none)
            else -> if (airport?.terminal?.length == 1) "0${airport.terminal}" else airport?.terminal
        }
        airport?.gate = airport?.gate ?: fragment.getString(R.string.none)
        itemViewBinding.tvEstimated.text = fragment.getString(R.string.estimated_time)
        itemViewBinding.tvActual.text = fragment.getString(R.string.actual_time)
        itemViewBinding.tvFlightNumber.text = fragment.getString(R.string.flight_number)
        itemViewBinding.tvTerminalGate.text = fragment.getString(R.string.terminal_gate)
        itemViewBinding.tvSlash.text = fragment.getString(R.string.slash)
        itemViewBinding.tvVerticalLine.text = fragment.getString(R.string.vertical_line)
        if (airport != null) {
            val color: Int = when (airport.remark) {
                fragment.getString(R.string.arrived)-> R.color.green
                fragment.getString(R.string.departed) -> R.color.green
                fragment.getString(R.string.cancelled) -> R.color.red
                fragment.getString(R.string.on_time) -> R.color.blue
                fragment.getString(R.string.schedule_change) -> R.color.orange
                else  -> R.color.green
            }
            itemViewBinding.tvRemark.setTextColor(ResourcesCompat.getColor(fragment.resources, color, fragment.activity?.theme))
            itemViewBinding.setVariable(BR.item, airport)
            itemViewBinding.executePendingBindings()
        }
    }

}