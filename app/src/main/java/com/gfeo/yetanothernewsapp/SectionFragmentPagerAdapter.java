package com.gfeo.yetanothernewsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A simple {@link FragmentPagerAdapter} implementation. Each fragment displays a news section,
 * for example, a {@link StoriesActivity.ArtSectionFragment}.
 *
 * @author gabrielfeo
 * @see StoriesActivity
 */
class SectionFragmentPagerAdapter extends FragmentPagerAdapter {

	private final String[] mTabNamesStringArray;

	/**
	 * @param tabNamesStringArray an array of Strings containing the tab names
	 * @param fragmentManager     a {@link FragmentManager} for the superclass' constructor
	 */
	SectionFragmentPagerAdapter(FragmentManager fragmentManager, String[] tabNamesStringArray) {
		super(fragmentManager);
		mTabNamesStringArray = tabNamesStringArray;
	}

	/**
	 * Returns the number of fragments available, equivalent to the length of the string array of
	 * tab names.
	 *
	 * @return the number of fragments available
	 */
	@Override
	public int getCount() {
		return mTabNamesStringArray.length;
	}

	/**
	 * Returns the page title according to the {@code position} parameter. The titles are fetched
	 * from a String array assigned by the constructor.
	 *
	 * @param position the current page position in the ViewPager
	 * @return the title of the current page
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return mTabNamesStringArray[position];
	}

	/**
	 * Returns the page fragment according to the {@code position} parameter. Each fragment
	 * displays a news section to the user, e.g. {@link StoriesActivity.ScienceSectionFragment}.
	 *
	 * @param position the current page position in the ViewPager
	 * @return the fragment to be displayed in the current page
	 * @see StoriesActivity
	 */
	@Override
	public Fragment getItem(int position) {
		SectionFragment fragment = null;
		switch (position) {
			case 0:
				fragment = new StoriesActivity.AllSectionFragment();
				break;
			case 1:
				fragment = new StoriesActivity.ArtSectionFragment();
				break;
			case 2:
				fragment = new StoriesActivity.BusinessSectionFragment();
				break;
			case 3:
				fragment = new StoriesActivity.CultureSectionFragment();
				break;
			case 4:
				fragment = new StoriesActivity.MediaSectionFragment();
				break;
			case 5:
				fragment = new StoriesActivity.MoneySectionFragment();
				break;
			case 6:
				fragment = new StoriesActivity.OpinionSectionFragment();
				break;
			case 7:
				fragment = new StoriesActivity.PoliticsSectionFragment();
				break;
			case 8:
				fragment = new StoriesActivity.ScienceSectionFragment();
				break;
			case 9:
				fragment = new StoriesActivity.TechSectionFragment();
				break;
		}
		return fragment;
	}
}
