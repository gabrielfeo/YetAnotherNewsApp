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
			                                        parent,
			                                        false
			                                       );
			convertView = layoutInflater.inflate(R.layout.listitem_stories,
			                                     (ViewGroup) container.findViewById(R.id.container)
			                                    );
			viewHolder = new ViewHolder();
			viewHolder.textViewHeadline = convertView.findViewById(R.id.listitem_stories_headline);
			viewHolder.textViewDateTime = convertView.findViewById(R.id.listitem_stories_datetime);
			viewHolder.textViewSection = convertView.findViewById(R.id.listitem_stories_section);
			viewHolder.textViewAuthor = convertView.findViewById(R.id.listitem_stories_author);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final Story currentStory = getItem(position);

		//Set the current story headline
		viewHolder.textViewHeadline.setText(currentStory.getHeadline());
		//Set the current story section
		//TODO Change to handle both the "All sections" tab and specific tabs
		String storySection = "in " + currentStory.getSection();
		viewHolder.textViewSection.setText(storySection);
		//Set the current story date and time
		String storyDateTime = currentStory.getDateTime();
		if (!storySection.isEmpty()) {
			storyDateTime += ", ";
		}
		viewHolder.textViewDateTime.setText(storyDateTime);
		//Set the current story author
		String storyAuthor = currentStory.getAuthor();
		if (storyAuthor.equals("Letters")) {
			storyAuthor = "Letter";
		} else if (!storyAuthor.isEmpty()) {
			storyAuthor = "by " + storyAuthor;
		}
		viewHolder.textViewAuthor.setText(storyAuthor);

		return convertView;
	}

	private class ViewHolder {
		TextView textViewHeadline;
		TextView textViewDateTime;
		TextView textViewSection;
		TextView textViewAuthor;
	}

}
