package com.gfeo.yetanothernewsapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StoryArrayAdapter(private val storyArrayList: ArrayList<Story>)
    : RecyclerView.Adapter<StoryArrayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.listitem_stories, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return storyArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val currentStory: Story = storyArrayList.get(position)
        holder?.textViewHeadline?.text = currentStory.headline
        holder?.textViewSection?.text = " in ${currentStory.section}"
        holder?.textViewDateTime?.text = currentStory.dateTime
        holder?.textViewAuthor?.text = when (currentStory.author) {
            "" -> "No author name available"
            "Letters" -> "Letter"
            else -> "by ${currentStory.author}"
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewHeadline: TextView =
                itemView.findViewById(R.id.listitem_stories_headline) as TextView
        var textViewDateTime: TextView =
                itemView.findViewById(R.id.listitem_stories_datetime) as TextView
        var textViewSection: TextView =
                itemView.findViewById(R.id.listitem_stories_section) as TextView
        var textViewAuthor: TextView =
                itemView.findViewById(R.id.listitem_stories_author) as TextView
    }

}