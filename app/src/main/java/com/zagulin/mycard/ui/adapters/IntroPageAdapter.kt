package com.zagulin.mycard.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class IntroPageAdapter(fm:FragmentManager, private val items:Array<Fragment>): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
      return  items.size
    }


}