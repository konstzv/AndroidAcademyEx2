package com.zagulin.mycard.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zagulin.mycard.R
import com.zagulin.mycard.ui.activity.MvpAppCompatActivity


class IntroPageAdapter(fm:FragmentManager, private val items:Array<Fragment>): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
      return  items.size
    }


}