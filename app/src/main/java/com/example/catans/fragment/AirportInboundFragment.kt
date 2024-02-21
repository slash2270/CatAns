package com.example.catans.fragment

import com.example.catans.base.BaseFragment
import com.example.catans.util.EnumAirport

class AirportInboundFragment : BaseFragment() {
    override fun initView() {
        enumAirport = EnumAirport.Inbound
    }

}