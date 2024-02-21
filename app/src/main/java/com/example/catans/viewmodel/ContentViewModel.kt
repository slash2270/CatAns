package com.example.catans.viewmodel

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.catans.adapter.ViewPagerAdapter
import com.example.catans.databinding.FragmentContentBinding
import com.example.catans.fragment.AirportDepartureFragment
import com.example.catans.fragment.AirportInboundFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContentViewModel: ViewModel() {

    fun init(binding: FragmentContentBinding, fragment: Fragment) {
        val mFragmentList: MutableList<Fragment> = arrayListOf(AirportInboundFragment(), AirportDepartureFragment())
        val mFragmentTitleList: MutableList<String> = arrayListOf(fragment.getString(com.example.catans.R.string.inbound_flight), fragment.getString(com.example.catans.R.string.departure_flight))
        val mFragmentIconList: ArrayList<Drawable?> = arrayListOf(ResourcesCompat.getDrawable(fragment.resources, com.example.catans.R.drawable.ic_launcher_foreground, fragment.activity?.theme), ResourcesCompat.getDrawable(fragment.resources, com.example.catans.R.drawable.ic_launcher_foreground, fragment.activity?.theme))
        binding.viewPager.adapter = ViewPagerAdapter(fragment, mFragmentList)
        binding.viewPager.currentItem = 0
        binding.viewPager.offscreenPageLimit = mFragmentList.size
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = mFragmentTitleList[position]
            tab.icon = mFragmentIconList[position]
        }.attach()
    }

}