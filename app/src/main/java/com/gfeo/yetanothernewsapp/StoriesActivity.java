package com.gfeo.yetanothernewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by gabrielfeo on 2018/04/11.
 */

public class StoriesActivity extends AppCompatActivity {

	static ArrayList<Story> storyArrayList;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);
		setSupportActionBar((Toolbar) findViewById(R.id.stories_toolbar));
		if (storyArrayList == null) {
			storyArrayList = new ArrayList<>();
		}
		storyArrayList.add(new Story(getString(R.string.test_headline),
		                             getString(R.string.test_datetime),
		                             getString(R.string.test_section),
		                             getString(R.string.test_author),
		                             getString(R.string.test_link))
		                  );
		final StoryArrayAdapter storyArrayAdapter = new StoryArrayAdapter(this, storyArrayList);
		ListView listView = findViewById(R.id.stories_listview);
		listView.setAdapter(storyArrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Story currentStory = storyArrayAdapter.getItem(position);
				Intent intent = new Intent(Intent.ACTION_VIEW,
				                           currentStory.getLink());
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivity(intent);
				}
			}
		});

	}
}
