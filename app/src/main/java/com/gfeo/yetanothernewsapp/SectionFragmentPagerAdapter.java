package com.gfeo.yetanothernewsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A simple {@link FragmentPagerAdapter} implementation. Each fragment displays a news section to
 * the user.
 *
 * @author gabrielfeo
 * @see StoriesActivity
 * @see SectionFragment
 */
class SectionFragmentPagerAdapter extends FragmentPagerAdapter {

	/** An array of Strings containing the tab names */
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
	 * Returns a new {@link SectionFragment} instance passing the current position {@code int} as
	 * an argument. Each fragment instance will display a different news section to the user. See
	 * the {@code SectionFragment} implementation for details.
	 *
	 * @param position the current page position in the ViewPager
	 * @return the {@code SectionFragment} instance to be displayed in the current page
	 * @see SectionFragment#newInstance(int)
	 */
	@Override
	public Fragment getItem(int position) {
		return SectionFragment.newInstance(position);
	}
}
