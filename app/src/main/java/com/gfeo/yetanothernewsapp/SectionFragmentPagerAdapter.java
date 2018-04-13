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
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String pageTitle = "";
		switch (position) {
			case 0:
				pageTitle = mContext.getString(R.string.fragment_all);
				break;
			case 1:
				pageTitle = mContext.getString(R.string.fragment_tech);
				break;
		}
		return pageTitle;

	}

	@Override
	public Fragment getItem(int position) {
		SectionFragment fragment = null;
		switch (position) {
			case 0:
				fragment = new SectionFragment();
				fragment.setSectionId(mContext.getString(R.string.sectionid_all));
				break;
			case 1:
				fragment = new SectionFragment();
				fragment.setSectionId(mContext.getString(R.string.fragment_tech));
				break;
		}
		return fragment;
	}
}
