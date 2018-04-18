package com.gfeo.yetanothernewsapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

/**
 * <p>Displays a single news section to the user. <br></p>
 *
 * @author gabrielfeo
 */

public class SectionFragment extends Fragment {

	/**
	 * An array of {@code ArrayList<Story>} objects. This field is static so that
	 * the loaded stories list of each section isn't lost when the fragments are reinstantiated.
	 * Each element in the array is an {@code ArrayList} of Stories of an individual news section.
	 *
	 * @see #initializeArrayListArray()
	 * @see #initializeArrayListArrayElement(int)
	 * @see #newInstance(int, int)
	 */
	private static ArrayList<Story>[] storyArrayListArray;
	/** An int loader ID for a {@link StoriesLoader}. */
	private int mLoaderId;
	/** A set of {@code StoriesLoaderCallbacks} for a {@link StoriesLoader} */
	private StoriesLoaderCallbacks mStoriesLoaderCallbacks;
	private StoryArrayAdapter mStoryArrayAdapter;

	public SectionFragment() {
		super();
	}

	/**
	 * Instantiates a {@code SectionFragment} with a {@link Bundle} indicating its current position
	 * in the {@code ViewPager}. This position {@code int} will be used to determine what stories
	 * list should be loaded and displayed, and also individual {@link StoriesLoaderCallbacks}
	 * and {@link StoriesLoader} of the current fragment.
	 *
	 * @param position the position fo the current fragment instance in the {@code ViewPager}
	 * @return a new {@code SectionFragment} instance with the {@code Bundle}
	 */
	static SectionFragment newInstance(int viewPagerCount, int position) {
		SectionFragment fragment = new SectionFragment();
		Bundle arguments = new Bundle();
		arguments.putInt("viewPagerCount", viewPagerCount);
		arguments.putInt("position", position);
		fragment.setArguments(arguments);
		return fragment;
	}

	/**
	 * Assigns a new {@link ArrayList} to the {@link #storyArrayListArray}, if it hasn't been
	 * assigned one already. The array length will be equivalent to the number returned by the
	 * {@link SectionFragmentPagerAdapter#getCount()}, passed in the arguments {@link Bundle}.
	 */
	private void initializeArrayListArray() {
		if (storyArrayListArray == null) {
			int numberOfSections = getArguments().getInt("viewPagerCount");
			storyArrayListArray = new ArrayList[numberOfSections];
		}
	}

	/**
	 * Assigns a new {@link ArrayList} to the {@link #storyArrayListArray} element at the specified
	 * position, if it hasn't been assigned one already.
	 *
	 * @param index an int indicating what element of the array should be null-checked
	 */
	private void initializeArrayListArrayElement(int index) {
		if (storyArrayListArray[index] == null) {
			storyArrayListArray[index] = new ArrayList<>();
		}
	}

	/**
	 * Gets the {@code ViewPager} position from the {@code arguments} {@link Bundle} and calls
	 * methods to initialize for the current {@link SectionFragment} a Stories list (if
	 * necessary), a set of {@link StoriesLoaderCallbacks}, and layout elements such as the
	 * {@code ListView}.
	 *
	 * @see #initializeArrayListArrayElement(int)
	 * @see #initializeStoriesLoaderCallbacks(int, View)
	 * @see #initializeSectionView(View, int)
	 */
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_section, container, false);
		int currentPosition = getArguments().getInt("position");
		initializeArrayListArray();
		initializeArrayListArrayElement(currentPosition);
		initializeStoriesLoaderCallbacks(currentPosition, view);
		return initializeSectionView(view, currentPosition);
	}

	/**
	 * Initializes and starts a loader with the current {@link #mLoaderId} and
	 * {@link #mStoriesLoaderCallbacks}.
	 *
	 * @see LoaderManager
	 */
	private void refreshStoriesList() {
		LoaderManager supportLoaderManager = getActivity().getSupportLoaderManager();
		Loader loaderWithCurrentId = supportLoaderManager.getLoader(mLoaderId);
		if (loaderWithCurrentId != null) {
			supportLoaderManager.destroyLoader(mLoaderId);
		}
		loaderWithCurrentId = supportLoaderManager.initLoader(mLoaderId, null,
		                                                      mStoriesLoaderCallbacks);
		if (loaderWithCurrentId != null) {
			loaderWithCurrentId.forceLoad();
		}
	}

	/**
	 * Sets a {@link android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener} to the
	 * {@link SwipeRefreshLayout} of the parameter {@link View}. The callback will call
	 * {@link #refreshStoriesList()}.
	 *
	 * @param view a {@code View} containing the {@code SwipeRefreshLayout}
	 */
	private void setOnRefreshAction(final View view) {
		((SwipeRefreshLayout) view.findViewById(R.id.fragment_swiperefreshlayout))
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refreshStoriesList();
					}
				});
	}

	/**
	 * <p>Called by the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} method.
	 * Configures layout elements, setting up the {@link SwipeRefreshLayout} and {@link ListView},
	 * and then calling {@link #refreshStoriesList()} to populate it if it's empty.</p>
	 *
	 * @param fragmentView the inflated {@link View} of the caller
	 * @return the {@code View} from the {@code fragmentView} parameter, after the layout
	 * configurations
	 * @see #setupListView(View, ArrayList)
	 * @see #setOnRefreshAction(View)
	 */
	private View initializeSectionView(View fragmentView,
	                                   int sectionPosition) {
		setupListView(fragmentView, storyArrayListArray[sectionPosition]);
		setOnRefreshAction(fragmentView);
		if (storyArrayListArray[sectionPosition].isEmpty()) {
			refreshStoriesList();
		}
		return fragmentView;
	}

	/**
	 * Creates a set {@code StoriesLoaderCallbacks} with the values of a String array named to
	 * the position number of the current news section.
	 *
	 * @see #getSectionStringArray(int)
	 * @see LoaderManager
	 */
	private void initializeStoriesLoaderCallbacks(int sectionPosition,
	                                              View fragmentView) {
		String[] sectionStringArray = getSectionStringArray(sectionPosition);
		mLoaderId = Integer.valueOf(sectionStringArray[0]);
		String sectionId = sectionStringArray[1];
		mStoriesLoaderCallbacks =
				new StoriesLoaderCallbacks(sectionId,
				                           storyArrayListArray[sectionPosition],
				                           fragmentView);
	}

	/**
	 * Gets a String array resource with the name of {@code sectionName} parameter. This array
	 * will contain a loader ID and a section ID for the {@link StoriesLoaderCallbacks}.
	 *
	 * @param sectionPosition an int that is part of the string-array name, for example "section_3"
	 * @return the String array resource
	 */
	private String[] getSectionStringArray(int sectionPosition) {
		String sectionStringArrayName = "section_" + sectionPosition;
		int sectionStringArrayResId = getContext().getResources()
		                                          .getIdentifier(sectionStringArrayName,
		                                                         "array",
		                                                         getContext().getPackageName());
		return getContext().getResources().getStringArray(sectionStringArrayResId);
	}

	/**
	 * Sets the layout's {@link ListView} {@code Adapter} and
	 * {@link android.widget.AdapterView.OnItemClickListener}.
	 *
	 * @param fragmentView   the {@code View} containing the {@code ListView}
	 * @param storyArrayList the {@link ArrayList} that will populate the {@code ListView}
	 */
	private void setupListView(View fragmentView, ArrayList<Story> storyArrayList) {
		mStoryArrayAdapter = new StoryArrayAdapter(getActivity(),
		                                           storyArrayList);
		ListView listView = fragmentView.findViewById(R.id.fragment_listview);
		listView.setAdapter(mStoryArrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Story currentStory = mStoryArrayAdapter.getItem(position);
				Intent intent = new Intent(Intent.ACTION_VIEW,
				                           currentStory.getLink());
				if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
					startActivity(intent);
				}
			}
		});
	}

	private static class StoriesLoader extends AsyncTaskLoader<ArrayList<Story>> {

		private final String mSectionId;
		private final ArrayList<Story> mStoryArrayList;

		StoriesLoader(Context context, String sectionId,
		              ArrayList<Story> storyArrayList) {
			super(context);
			mSectionId = sectionId;
			mStoryArrayList = storyArrayList;
		}

		@Override
		public ArrayList<Story> loadInBackground() {
			URL queryUrl = QueryUtils.buildQueryUrl(mSectionId);
			String jsonResponse = QueryUtils.makeHttpRequest(queryUrl);
			QueryUtils.parseJsonToArrayList(jsonResponse, mStoryArrayList);
			return mStoryArrayList;
		}
	}

	private class StoriesLoaderCallbacks implements LoaderCallbacks {

		private final String mSectionId;
		private final ArrayList<Story> mStoryArrayList;
		private View mStoriesView;

		StoriesLoaderCallbacks(String sectionId,
		                       ArrayList<Story> storyArrayList,
		                       View storiesView) {
			super();
			mSectionId = sectionId;
			mStoryArrayList = storyArrayList;
			mStoriesView = storiesView;
		}

		@Override
		public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
			if (existsActiveNetworkConnection()) {
				showProgressBar(mStoriesView);
				return new StoriesLoader(getActivity(), mSectionId, mStoryArrayList);
			} else {
				showNoConnectionView(mStoriesView);
				return null;
			}
		}

		@Override
		public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {
			showStoriesList(mStoriesView);
			mStoryArrayAdapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(android.support.v4.content.Loader loader) {
		}

		/**
		 * Queries the {@link ConnectivityManager} SystemService for the active network's
		 * information
		 * . Provides the caller with basic connectivity information (has active connection or
		 * not) useful for flow control on Internet related functions.
		 *
		 * @return a boolean stating whether an active connection (or an impending,
		 * "connecting") is available or not.
		 */
		private boolean existsActiveNetworkConnection() {
			ConnectivityManager cm = (ConnectivityManager)
					getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork;
			try {
				activeNetwork = cm.getActiveNetworkInfo();
			} catch (NullPointerException e) {
				activeNetwork = null;
			}
			return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
		}

		private void showProgressBar(View view) {
			SwipeRefreshLayout swipeRefreshLayout =
					view.findViewById(R.id.fragment_swiperefreshlayout);
			if (swipeRefreshLayout.isRefreshing()) {
				return;
			}
			view.findViewById(R.id.fragment_textview_no_connection)
			    .setVisibility(View.GONE);
			view.findViewById(R.id.fragment_swiperefreshlayout).setVisibility(View.GONE);
			view.findViewById(R.id.fragment_progressbar).setVisibility(View.VISIBLE);
		}

		private void showStoriesList(View view) {
			SwipeRefreshLayout swipeRefreshLayout =
					view.findViewById(R.id.fragment_swiperefreshlayout);
			if (swipeRefreshLayout.isRefreshing()) {
				swipeRefreshLayout.setRefreshing(false);
			}
			view.findViewById(R.id.fragment_textview_no_connection)
			    .setVisibility(View.GONE);
			view.findViewById(R.id.fragment_progressbar).setVisibility(View.GONE);
			view.findViewById(R.id.fragment_swiperefreshlayout).setVisibility(View.VISIBLE);
		}

		private void showNoConnectionView(View view) {
			SwipeRefreshLayout swipeRefreshLayout =
					view.findViewById(R.id.fragment_swiperefreshlayout);
			if (swipeRefreshLayout.isRefreshing()) {
				swipeRefreshLayout.setRefreshing(false);
			}
			view.findViewById(R.id.fragment_swiperefreshlayout).setVisibility(View.GONE);
			view.findViewById(R.id.fragment_progressbar).setVisibility(View.GONE);
			view.findViewById(R.id.fragment_textview_no_connection)
			    .setVisibility(View.VISIBLE);
		}

	}

}
