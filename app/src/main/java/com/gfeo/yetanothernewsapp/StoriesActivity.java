package com.gfeo.yetanothernewsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Displays a {@link ViewPager} of {@link SectionFragment} objects to the user. Each fragment
 * View represents a different news section.
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
	 * {@link SectionFragmentPagerAdapter}. Also, sets the {@link TabLayout} View to work with the
	 * {@code ViewPager}.
	 */
	private void setupViewPager() {
		ViewPager viewPager = findViewById(R.id.stories_viewpager);
		TabLayout tabLayout = (findViewById(R.id.stories_tablayout));
		String[] tabNamesStringArray = getResources().getStringArray(R.array.tab_names);
		viewPager.setAdapter(new SectionFragmentPagerAdapterKt(getSupportFragmentManager(),
		                                                     tabNamesStringArray));
		tabLayout.setupWithViewPager(viewPager);
	}

}
