package com.gfeo.yetanothernewsapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import io.reactivex.disposables.Disposable
import kotlin.properties.Delegates


class SectionFragmentKt : Fragment() {

    private var sectionPosition: Int by Delegates.notNull()
    private lateinit var sectionId: String
    private lateinit var storyArrayAdapter: StoryArrayAdapter
    private lateinit var rxDisposable: Disposable

    companion object {
        //TODO Does this work as a static field?
        @JvmStatic
        private lateinit var storyArrayListArray: Array<ArrayList<Story>>

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
        initializeArrayListArray()
        initializeArrayListArrayElement()
        getSectionStringArray()
        return TODO("setupSectionView(view)")
    }

    private fun initializeArrayListArray() {
        //TODO Why is this always false?
        if (storyArrayListArray == null) {
            val numberOfSections = arguments.getInt("viewPagerCount")
            storyArrayListArray =
                    Array<ArrayList<Story>>(numberOfSections, { _ -> ArrayList() })
        }
    }

    private fun initializeArrayListArrayElement() {
        //TODO Why is this always false?
        if (storyArrayListArray[sectionPosition] == null) {
            storyArrayListArray[sectionPosition] = ArrayList()
        }
    }

    private fun setOnRefreshAction(layoutView: View) {
        layoutView.findViewById<SwipeRefreshLayout>(R.id.fragment_swiperefreshlayout)
                .setOnRefreshListener { TODO("loadStories(layoutView)") }
    }

    private fun getSectionStringArray() {
        val sectionStringArrayName = "section_${sectionPosition}"
        val sectionStringArrayResId: Int = context.resources.getIdentifier(sectionStringArrayName,
                "array",
                context.packageName)
        val sectionStringArray: Array<String> = context.resources
                .getStringArray(sectionStringArrayResId)
        sectionId = sectionStringArray[1]
    }

    private fun setupListView(fragmentView: View) {
        storyArrayAdapter = StoryArrayAdapter(activity, storyArrayListArray[sectionPosition])
        val listView: ListView = fragmentView.findViewById(R.id.fragment_listview)
        listView.adapter = storyArrayAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val currentStory: Story = storyArrayAdapter.getItem(position)
            val intent = Intent(Intent.ACTION_VIEW, currentStory.link)
            if (intent.resolveActivity(activity.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

}