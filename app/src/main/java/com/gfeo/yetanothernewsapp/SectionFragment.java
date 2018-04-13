package com.gfeo.yetanothernewsapp;

import android.content.Intent;
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
	static ArrayList<Story> storyArrayList;
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

}
