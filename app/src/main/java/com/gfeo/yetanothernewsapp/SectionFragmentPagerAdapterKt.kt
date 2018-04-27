package com.gfeo.yetanothernewsapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by gabrielfeo on 2018/04/27.
 */
class SectionFragmentPagerAdapterKt(private val fragmentManager: FragmentManager,
                                    private val tabNamesStringArray: Array<String>)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return SectionFragment.newInstance(count, position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabNamesStringArray[position]
    }

    override fun getCount(): Int {
        return tabNamesStringArray.size
    }
}