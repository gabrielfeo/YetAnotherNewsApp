package com.gfeo.yetanothernewsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StoryArrayAdapter(private val mContext: Context,
                        private val storyArrayList: ArrayList<Story>)
    : ArrayAdapter<Story>(mContext, 0, storyArrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView;
        val viewHolder: ViewHolder
        if (view == null) {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            val container: View = layoutInflater.inflate(R.layout.container, parent, false)
            view = layoutInflater.inflate(R.layout.listitem_stories,
                    container.findViewById(R.id.container), false)
            viewHolder = ViewHolder()
            viewHolder.textViewHeadline = view.findViewById(R.id.listitem_stories_headline)
            viewHolder.textViewDateTime = view.findViewById(R.id.listitem_stories_datetime)
            viewHolder.textViewSection = view.findViewById(R.id.listitem_stories_section)
            viewHolder.textViewAuthor = view.findViewById(R.id.listitem_stories_author)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val currentStory: Story = getItem(position)

        //Set the current story headline
        viewHolder.textViewHeadline.text = currentStory.headline
        //Set the current story section
        val sectionText = " in ${currentStory.section}"
        viewHolder.textViewSection.text = sectionText
        //Set the current story date and time
        viewHolder.textViewDateTime.text = currentStory.dateTime
        //Set the current story author, if available
        viewHolder.textViewAuthor.text = when (currentStory.author) {
            "" -> "No author name available"
            "Letters" -> "Letter"
            else -> "by ${currentStory.author}"
        }
        return view!!
    }

    private class ViewHolder {
        internal lateinit var textViewHeadline: TextView
        internal lateinit var textViewDateTime: TextView
        internal lateinit var textViewSection: TextView
        internal lateinit var textViewAuthor: TextView
    }

}