package com.gfeo.yetanothernewsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gabrielfeo on 2018/04/13.
 */

class SectionFragmentPagerAdapter extends FragmentPagerAdapter {

	private Context mContext;

	SectionFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
		super(fragmentManager);
		mContext = context;
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String pageTitle = "";
		switch (position) {
			case 0:
				pageTitle = mContext.getString(R.string.tabname_all);
				break;
			case 1:
				pageTitle = mContext.getString(R.string.tabname_art);
				break;
			case 2:
				pageTitle = mContext.getString(R.string.tabname_business);
				break;
			case 3:
				pageTitle = mContext.getString(R.string.tabname_culture);
				break;
			case 4:
				pageTitle = mContext.getString(R.string.tabname_media);
				break;
			case 5:
				pageTitle = mContext.getString(R.string.tabname_money);
				break;
			case 6:
				pageTitle = mContext.getString(R.string.tabname_opinion);
				break;
			case 7:
				pageTitle = mContext.getString(R.string.tabname_politics);
				break;
			case 8:
				pageTitle = mContext.getString(R.string.tabname_science);
				break;
			case 9:
				pageTitle = mContext.getString(R.string.tabname_technology);
				break;
		}
		return pageTitle;

	}

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
				fragment = new StoriesActivity.TechSectionFragment();;
				break;
		}
		return fragment;
	}
}
