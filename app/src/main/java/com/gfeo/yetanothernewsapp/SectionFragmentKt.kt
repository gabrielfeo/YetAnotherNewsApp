package com.gfeo.yetanothernewsapp

import android.os.Bundle
import android.support.v4.app.Fragment

class SectionFragmentKt : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(viewPagerCount: Int, position: Int): SectionFragmentKt {
            val fragment = SectionFragmentKt()
            val arguments = Bundle()
            arguments.putInt("viewPagerCount", viewPagerCount)
            arguments.putInt("position", position)
            fragment.arguments = arguments
            return fragment
        }
    }

}