package com.gfeo.yetanothernewsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by gabrielfeo on 2018/04/11.
 */

public class StoriesActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);
		setSupportActionBar((Toolbar) findViewById(R.id.stories_toolbar));

		ViewPager viewPager = findViewById(R.id.stories_viewpager);
		TabLayout tabLayout = (findViewById(R.id.stories_tablayout));

		viewPager.setAdapter(new SectionFragmentPagerAdapter(this,
		                                                     getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);
	}

	public static class AllSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public AllSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeList(storyArrayList);
			setupListView(view, storyArrayList);
			initializeStoriesLoader(0, "", storyArrayList);
			return view;
		}

	}

	public static class TechSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public TechSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeList(storyArrayList);
			setupListView(view, storyArrayList);
			initializeStoriesLoader(1, "technology", storyArrayList);
			return view;
		}
	}

}
