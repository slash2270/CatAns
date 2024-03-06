package com.example.catans.fragment

import com.example.catans.base.BaseFragment
import com.example.catans.util.EnumUtils

class AirportDepartureFragment : BaseFragment() {
    override fun initView() {
        enumUtils = EnumUtils.Departure
    }

}