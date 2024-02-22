package com.example.catans.viewmodel

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.catans.R
import com.example.catans.adapter.ViewPagerAdapter
import com.example.catans.databinding.FragmentContentBinding
import com.example.catans.fragment.AirportDepartureFragment
import com.example.catans.fragment.AirportInboundFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContentViewModel: ViewModel() {

    fun init(binding: FragmentContentBinding, fragment: Fragment) {
        val mFragmentList: MutableList<Fragment> = arrayListOf(AirportInboundFragment(), AirportDepartureFragment())
        val mFragmentViewList: ArrayList<View> = arrayListOf(
            LayoutInflater.from(fragment.context).inflate(R.layout.item_tab_entry, null),
            LayoutInflater.from(fragment.context).inflate(R.layout.item_tab_departure, null),
        )
        binding.viewPager.adapter = ViewPagerAdapter(fragment, mFragmentList)
        binding.viewPager.currentItem = 0
        binding.viewPager.offscreenPageLimit = mFragmentList.size
        binding.tabLayout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(fragment.resources, R.color.grey_500, fragment.activity?.theme))
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = mFragmentViewList[position]
        }.attach()
    }

}