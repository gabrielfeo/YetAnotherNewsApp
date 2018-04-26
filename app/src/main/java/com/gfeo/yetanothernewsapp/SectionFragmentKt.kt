package com.gfeo.yetanothernewsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import kotlin.properties.Delegates


class SectionFragmentKt : Fragment() {

    private var sectionPosition: Int by Delegates.notNull()
    private lateinit var storyArrayListArray: ArrayList<Story>
    private lateinit var sectionId: String
    private lateinit var storyArrayAdapter: StoryArrayAdapter
    private lateinit var rxDisposable: Disposable

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_section, container, false)
        sectionPosition = arguments.getInt("position")
        TODO("call other initializer methods")
        return view
    }

}