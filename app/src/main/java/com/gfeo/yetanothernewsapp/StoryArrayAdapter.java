package com.gfeo.yetanothernewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gabrielfeo on 2018/04/13.
 */

class StoryArrayAdapter extends ArrayAdapter<Story> {

	private Context mContext;

	StoryArrayAdapter(Context context, ArrayList<Story> storyArrayList) {
		super(context, 0, storyArrayList);
		mContext = context;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			View container = layoutInflater.inflate(R.layout.container,
			                                        null,
			                                        false
			                                       );
			//TODO Change to handle both the "All sections" tab and specific tabs
			convertView = layoutInflater.inflate(R.layout.listitem_stories_generic,
			                                     (ViewGroup) container.findViewById(R.id.container)
			                                    );
			viewHolder = new ViewHolder();
			viewHolder.storyHeadline = convertView.findViewById(R.id.listitem_stories_headline);
			viewHolder.storyDateTime = convertView.findViewById(R.id.listitem_stories_datetime);
			viewHolder.storySection = convertView.findViewById(R.id.listitem_stories_section);
			viewHolder.storyAuthor = convertView.findViewById(R.id.listitem_stories_author);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final Story currentStory = getItem(position);

		viewHolder.storyHeadline.setText(currentStory.getHeadline());
		viewHolder.storyDateTime.setText(currentStory.getDateTime());
		//TODO Change to handle both the "All sections" tab and specific tabs
		viewHolder.storySection.setText(currentStory.getSection());
		viewHolder.storyAuthor.setText(currentStory.getAuthor());

		return convertView;
	}

	private class ViewHolder {
		TextView storyHeadline;
		TextView storyDateTime;
		TextView storySection;
		TextView storyAuthor;
	}

}
