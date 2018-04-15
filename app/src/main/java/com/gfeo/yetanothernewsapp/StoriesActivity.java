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
		setupViewPager();
	}

	private void setupViewPager() {
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
			return initializeSectionView("all", view, storyArrayList);

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
			return initializeSectionView("art", view, storyArrayList);

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
			return initializeSectionView("business", view, storyArrayList);

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
			return initializeSectionView("culture", view, storyArrayList);

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
			return initializeSectionView("media", view, storyArrayList);

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
			return initializeSectionView("money", view, storyArrayList);

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
			return initializeSectionView("opinion", view, storyArrayList);

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
			return initializeSectionView("politics", view, storyArrayList);

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
			return initializeSectionView("science", view, storyArrayList);

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
			return initializeSectionView("technology", view, storyArrayList);

		}
	}

}
