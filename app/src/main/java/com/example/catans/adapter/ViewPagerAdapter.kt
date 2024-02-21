package com.example.catans.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: Fragment, mList: List<Fragment>) : FragmentStateAdapter(fa) {

    private var mList: List<Fragment>

    init {
        this.mList = mList
    }

    override fun getItemCount(): Int = mList.size

    override fun createFragment(position: Int): Fragment = mList[position]

}