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
 * Displays a {@link ViewPager} of {@link SectionFragment} subclasses to the user. Each fragment
 * View represents a news section.
 *
 * @author gabrielfeo
 */

public class StoriesActivity extends AppCompatActivity {

	/**
	 * Sets the layout to be displayed in the Activity, the Toolbar and configures the layout's
	 * {@link ViewPager} using the {@link #setupViewPager()} method.
	 */
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);
		setSupportActionBar((Toolbar) findViewById(R.id.stories_toolbar));
		setupViewPager();
	}

	/**
	 * Configures the Activity's layout's {@link ViewPager} with a
	 * {@link SectionFragmentPagerAdapter}. Also sets the {@link TabLayout} View to work with the
	 * {@code ViewPager}.
	 */
	private void setupViewPager() {
		ViewPager viewPager = findViewById(R.id.stories_viewpager);
		TabLayout tabLayout = (findViewById(R.id.stories_tablayout));
		String[] tabNames = getResources().getStringArray(R.array.tab_names);
		viewPager.setAdapter(new SectionFragmentPagerAdapter(getSupportFragmentManager(),
		                                                     tabNames));
		tabLayout.setupWithViewPager(viewPager);
	}

	/**
	 * A {@code SectionFragment} representing the "All" section, namely, a section containing
	 * news stories from all sections.
	 *
	 * @author gabrielfeo
	 */
	public static class AllSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public AllSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("all", view, storyArrayList);

		}

	}

	/**
	 * A {@code SectionFragment} representing the "Art and Design" section.
	 *
	 * @author gabrielfeo
	 */
	public static class ArtSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public ArtSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("art", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Business" section.
	 *
	 * @author gabrielfeo
	 */
	public static class BusinessSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public BusinessSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("business", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Culture" section.
	 *
	 * @author gabrielfeo
	 */
	public static class CultureSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public CultureSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("culture", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Media" section.
	 *
	 * @author gabrielfeo
	 */
	public static class MediaSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public MediaSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("media", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Money" section.
	 *
	 * @author gabrielfeo
	 */
	public static class MoneySectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public MoneySectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("money", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Opinion" section.
	 *
	 * @author gabrielfeo
	 */
	public static class OpinionSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public OpinionSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("opinion", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Politics" section.
	 *
	 * @author gabrielfeo
	 */
	public static class PoliticsSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public PoliticsSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("politics", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Science" section.
	 *
	 * @author gabrielfeo
	 */
	public static class ScienceSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public ScienceSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("science", view, storyArrayList);

		}
	}

	/**
	 * A {@code SectionFragment} representing the "Technology" section.
	 *
	 * @author gabrielfeo
	 */
	public static class TechSectionFragment extends SectionFragment {

		/**
		 * A static {@link ArrayList} containing the news stories that should be loaded into the
		 * {@link android.support.v4.app.Fragment}'s layout's {@link android.widget.ListView}.
		 */
		private static ArrayList<Story> storyArrayList;

		public TechSectionFragment() {
			super();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
				Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			storyArrayList = initializeArrayListField(storyArrayList);
			return initializeSectionView("technology", view, storyArrayList);

		}
	}

}
