package com.gfeo.yetanothernewsapp

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

class StoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)
        setSupportActionBar(findViewById(R.id.stories_toolbar))
        setupViewPager()
    }

    private fun setupViewPager(){
        val viewPager = findViewById<ViewPager>(R.id.stories_viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.stories_tablayout)
        val tabNamesStringArray: Array<String> = resources.getStringArray(R.array.tab_names)
        viewPager.adapter = SectionFragmentPagerAdapter(supportFragmentManager,
                tabNamesStringArray)
        tabLayout.setupWithViewPager(viewPager)
    }
}