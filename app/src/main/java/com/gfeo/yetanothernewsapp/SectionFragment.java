package com.gfeo.yetanothernewsapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
 * Created by gabrielfeo on 2018/04/13.
 */

public class SectionFragment extends Fragment {
	private StoryArrayAdapter storyArrayAdapter;

	public SectionFragment() {
		super();
	}

	protected static ArrayList<Story> initializeStoryArrayList(ArrayList<Story> storyArrayList) {
		if (storyArrayList == null) {
			storyArrayList = new ArrayList<>();
		}
		return storyArrayList;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_section, container, false);
	}

	protected void initializeStoriesList(int loaderId,
	                                     StoriesLoaderCallbacks storiesLoaderCallbacks,
	                                     ArrayList<Story> storyArrayList) {
		if (storyArrayList.isEmpty()) {
			refreshStoriesList(loaderId, storiesLoaderCallbacks, storyArrayList);
		}
	}

	private void refreshStoriesList(int loaderId,
	                                StoriesLoaderCallbacks storiesLoaderCallbacks,
	                                ArrayList<Story> storyArrayList) {
		Loader loader = getActivity().getSupportLoaderManager()
		                             .restartLoader(loaderId, null, storiesLoaderCallbacks);
		if (loader != null) {
			loader.forceLoad();
		}
	}

	private void setOnRefreshAction(final View view,
	                                final ArrayList<Story> storyArrayList,
	                                final int loaderId,
	                                final StoriesLoaderCallbacks storiesLoaderCallbacks) {
		((SwipeRefreshLayout) view.findViewById(R.id.fragment_swiperefreshlayout))
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refreshStoriesList(loaderId, storiesLoaderCallbacks, storyArrayList);
					}
				});
	}

	protected View initializeSectionView(String sectionName,
	                                     View fragmentView,
	                                     ArrayList<Story> storyArrayList) {
		setupListView(fragmentView, storyArrayList);
		String sectionStringArrayName = "section_" + sectionName;
		int sectionStringArrayResId = getContext().getResources()
		                                          .getIdentifier(sectionStringArrayName,
		                                                         "array",
		                                                         getContext().getPackageName());
		String[] sectionStringArray = getContext().getResources()
		                                          .getStringArray(sectionStringArrayResId);
		int loaderId = Integer.valueOf(sectionStringArray[0]);
		String sectionId = sectionStringArray[1];
		StoriesLoaderCallbacks storiesLoaderCallbacks =
				new StoriesLoaderCallbacks(sectionId, storyArrayList, fragmentView);
		initializeStoriesList(loaderId, storiesLoaderCallbacks, storyArrayList);
		setOnRefreshAction(fragmentView, storyArrayList, loaderId, storiesLoaderCallbacks);
		return fragmentView;
	}

	private void setupListView(View fragmentView, ArrayList<Story> storyArrayList) {
		storyArrayAdapter = new StoryArrayAdapter(getActivity(),
		                                          storyArrayList);
		ListView listView = fragmentView.findViewById(R.id.fragment_listview);
		listView.setAdapter(storyArrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Story currentStory = storyArrayAdapter.getItem(position);
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
		private String mSearchQuery = "";

		StoriesLoader(Context context, String searchQuery, String sectionId,
		              ArrayList<Story> storyArrayList) {
			super(context);
			mSearchQuery = searchQuery;
			mSectionId = sectionId;
			mStoryArrayList = storyArrayList;
		}

		@Override
		public ArrayList<Story> loadInBackground() {
			URL queryUrl = QueryUtils.buildQueryUrl(mSearchQuery, mSectionId);
			String jsonResponse = QueryUtils.makeHttpRequest(queryUrl);
			QueryUtils.parseJsonToArrayList(jsonResponse, mStoryArrayList);
			return mStoryArrayList;
		}
	}

	protected class StoriesLoaderCallbacks implements LoaderCallbacks {

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
				return new StoriesLoader(getActivity(), "", mSectionId, mStoryArrayList);
			} else {
				showNoConnectionView(mStoriesView);
				return null;
			}
		}

		@Override
		public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {
			showStoriesList(mStoriesView);
			storyArrayAdapter.notifyDataSetChanged();
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
