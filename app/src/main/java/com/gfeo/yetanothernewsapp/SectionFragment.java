package com.gfeo.yetanothernewsapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
	//TODO Can be private?
	protected StoryArrayAdapter storyArrayAdapter;

	public SectionFragment() {
		super();
	}

	protected static ArrayList<Story> initializeList(ArrayList<Story> storyArrayList) {
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

	protected void initializeStoriesLoader(int loaderId,
	                                       String sectionId,
	                                       ArrayList<Story> storyArrayList) {
		if (storyArrayList.isEmpty()) {
			final LoaderManager.LoaderCallbacks loaderCallbacks =
					new StoriesLoaderCallbacks(sectionId, storyArrayList);
			getLoaderManager().initLoader(loaderId, null, loaderCallbacks).forceLoad();
		}
	}

	protected void setupListView(View fragmentView, ArrayList<Story> storyArrayList) {
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

	//TODO Can multiple fragment instances use this?
	protected static class StoriesLoader extends AsyncTaskLoader<ArrayList<Story>> {

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

		StoriesLoaderCallbacks(String sectionId, ArrayList<Story> storyArrayList) {
			super();
			mSectionId = sectionId;
			mStoryArrayList = storyArrayList;
		}

		@Override
		public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
			//TODO get activity?
			return new StoriesLoader(getActivity(), "", mSectionId, mStoryArrayList);
		}

		@Override
		public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {
			storyArrayAdapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(android.support.v4.content.Loader loader) {
		}
	}

	;

}
