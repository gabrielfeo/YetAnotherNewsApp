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
		setupVIewPager();
	}

	private void setupVIewPager() {
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
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("all", view, storyArrayList);
			return view;
		}

	}

	public static class ArtSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public ArtSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("art", view, storyArrayList);
			return view;
		}
	}

	public static class BusinessSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public BusinessSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("business", view, storyArrayList);
			return view;
		}
	}

	public static class CultureSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public CultureSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("culture", view, storyArrayList);
			return view;
		}
	}

	public static class MediaSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public MediaSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("media", view, storyArrayList);
			return view;
		}
	}

	public static class MoneySectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public MoneySectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("money", view, storyArrayList);
			return view;
		}
	}

	public static class OpinionSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public OpinionSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("opinion", view, storyArrayList);
			return view;
		}
	}

	public static class PoliticsSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public PoliticsSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("politics", view, storyArrayList);
			return view;
		}
	}

	public static class ScienceSectionFragment extends SectionFragment {

		private static ArrayList<Story> storyArrayList;

		public ScienceSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("science", view, storyArrayList);
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
			storyArrayList = initializeStoryArrayList(storyArrayList);
			initializeSectionView("technology", view, storyArrayList);
			return view;
		}
	}

}
