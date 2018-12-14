package com.arthurivanets.sample

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter

/**
 *
 */
class VideoItemsRecyclerViewAdapter(
    context : Context,
    items : MutableList<VideoItem>,
    private val resources : VideoItemResources
) : TrackableRecyclerViewAdapter<Long, VideoItem, BaseItem.ViewHolder<*>>(context, items) {


    override fun getResources() : ItemResources? {
        return resources
    }


}