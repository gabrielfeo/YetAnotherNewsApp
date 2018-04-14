package com.gfeo.yetanothernewsapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by gabrielfeo on 2018/04/13.
 */

public class SectionFragment extends Fragment {
	//TODO Can be private?
	static ArrayList<Story> storyArrayList;
	private final LoaderManager.LoaderCallbacks loaderCallbacks =
			new LoaderManager.LoaderCallbacks() {

				@Override
				public Loader onCreateLoader(int id, Bundle args) {
					//TODO get activity?
					return new StoriesLoader(getActivity());
				}

				@Override
				public void onLoadFinished(Loader loader, Object data) {

				}

				@Override
				public void onLoaderReset(Loader loader) {
				}
			};
	//TODO Handle empty sectionId
	String mSectionId;

	public SectionFragment() {
		super();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_section, container, false);
		setupListView(view, storyArrayList);
		return view;
	}

	void setSectionId(String sectionId) {
		mSectionId = sectionId;
	}

	void setupListView(View fragmentView, ArrayList<Story> storyArrayList) {
		if (storyArrayList == null) {
			storyArrayList = new ArrayList<>();
		}
		//TODO remove
		storyArrayList.add(new Story(getString(R.string.test_headline),
		                             getString(R.string.test_datetime),
		                             getString(R.string.test_section),
		                             getString(R.string.test_author),
		                             getString(R.string.test_link)));
		final StoryArrayAdapter storyArrayAdapter = new StoryArrayAdapter(getActivity(),
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
	private static class StoriesLoader extends AsyncTaskLoader<ArrayList<Story>> {

		StoriesLoader(Context context) {
			super(context);
		}

		@Override
		public ArrayList<Story> loadInBackground() {
			return null;
		}
	}

}
