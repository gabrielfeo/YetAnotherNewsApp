package com.gfeo.yetanothernewsapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URL
import kotlin.properties.Delegates


class SectionFragmentKt : Fragment() {

    //TODO LOG_TAG constant
    var sectionPosition: Int by Delegates.notNull()
    private lateinit var sectionId: String
    private lateinit var storyArrayAdapter: StoryArrayAdapter
    private lateinit var rxDisposable: Disposable

    companion object {
        //TODO Does this work as a static field?
        private lateinit var storyArrayListArray: Array<ArrayList<Story>>

        private fun initializeArrayListArray(arguments: Bundle) {
            try {
                storyArrayListArray.size
            } catch (e: UninitializedPropertyAccessException) {
                val numberOfSections = arguments.getInt("viewPagerCount")
                storyArrayListArray =
                        Array<ArrayList<Story>>(numberOfSections, { _ -> ArrayList() })
            }
        }

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
        initializeArrayListArray(arguments)
        getSectionStringArray()
        return setupSectionView(view)
    }

    private fun setupSectionView(fragmentView: View?): View? {
        setupListView(fragmentView)
        setOnRefreshAction(fragmentView)
        if (storyArrayListArray[sectionPosition].isEmpty()) loadStories(fragmentView)
        return fragmentView
    }

    private fun setOnRefreshAction(layoutView: View?) {
        layoutView?.findViewById<SwipeRefreshLayout>(R.id.fragment_swiperefreshlayout)
                ?.setOnRefreshListener { loadStories(layoutView) }
    }

    private fun getSectionStringArray() {
        val sectionStringArrayName = "section_$sectionPosition"
        val sectionStringArrayResId: Int = context.resources.getIdentifier(sectionStringArrayName,
                "array",
                context.packageName)
        val sectionStringArray: Array<String> = context.resources
                .getStringArray(sectionStringArrayResId)
        sectionId = sectionStringArray[1]
    }

    private fun setupListView(fragmentView: View?) {
        storyArrayAdapter = StoryArrayAdapter(activity, storyArrayListArray[sectionPosition])
        val listView: ListView? = fragmentView?.findViewById(R.id.fragment_listview)
        listView?.adapter = storyArrayAdapter
        listView?.setOnItemClickListener { _, _, position, _ ->
            val currentStory: Story = storyArrayAdapter.getItem(position)
            val intent = Intent(Intent.ACTION_VIEW, currentStory.link)
            if (intent.resolveActivity(activity.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun existsActiveNetworkConnection(): Boolean {
        val cm: ConnectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?
        activeNetwork = cm.activeNetworkInfo
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)
    }

    private fun showProgressBar(view: View?) {
        val swipeRefreshLayout =
                view?.findViewById<SwipeRefreshLayout>(R.id.fragment_swiperefreshlayout)
        if (swipeRefreshLayout?.isRefreshing == true) return
        view?.findViewById<View>(R.id.fragment_textview_no_connection)?.visibility = View.GONE
        view?.findViewById<View>(R.id.fragment_swiperefreshlayout)?.visibility = View.GONE
        view?.findViewById<View>(R.id.fragment_progressbar)?.visibility = View.VISIBLE
    }

    private fun showStoriesList(view: View?) {
        val swipeRefreshLayout =
                view?.findViewById<SwipeRefreshLayout>(R.id.fragment_swiperefreshlayout)
        if (swipeRefreshLayout?.isRefreshing == true) swipeRefreshLayout.isRefreshing = false
        view?.findViewById<View>(R.id.fragment_textview_no_connection)?.visibility = View.GONE
        view?.findViewById<View>(R.id.fragment_progressbar)?.visibility = View.GONE
        view?.findViewById<View>(R.id.fragment_swiperefreshlayout)?.visibility = View.VISIBLE
    }

    private fun showNoConnectionView(view: View?) {
        val swipeRefreshLayout =
                view?.findViewById<SwipeRefreshLayout>(R.id.fragment_swiperefreshlayout)
        if (swipeRefreshLayout?.isRefreshing == true) swipeRefreshLayout.isRefreshing = false
        view?.findViewById<View>(R.id.fragment_swiperefreshlayout)?.visibility = View.GONE
        view?.findViewById<View>(R.id.fragment_progressbar)?.visibility = View.GONE
        view?.findViewById<View>(R.id.fragment_textview_no_connection)?.visibility = View.VISIBLE
    }

    private fun loadStories(storiesView: View?) {
        val storyArrayListObservable: Observable<ArrayList<Story>> = Observable.fromCallable {
            val queryUrl: URL = QueryUtils.buildQueryUrl(sectionId)
            val jsonResponse: String = QueryUtils.makeHttpRequest(queryUrl)
            QueryUtils.parseJsonToArrayList(jsonResponse, storyArrayListArray[sectionPosition])
        }
        storyArrayListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(initializeStoriesObserver(storiesView))
    }

    private fun initializeStoriesObserver(storiesView: View?): Observer<ArrayList<Story>> {
        return object : Observer<ArrayList<Story>> {
            override fun onSubscribe(d: Disposable) {
                if (existsActiveNetworkConnection()) {
                    showProgressBar(storiesView)
                    rxDisposable = d
                } else {
                    showNoConnectionView(storiesView)
                    d.dispose()
                }
            }

            override fun onNext(t: ArrayList<Story>) {}

            override fun onError(e: Throwable) {
                Log.e("SectionFragment", "Error on storyArrayListObservable", e)
            }

            override fun onComplete() {
                showStoriesList(storiesView)
                storyArrayAdapter.notifyDataSetChanged()
            }
        }
    }

}