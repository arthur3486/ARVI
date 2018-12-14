package com.arthurivanets.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item

class VideoItem(itemModel : Video) : BaseItem<Video, VideoItemViewHolder, VideoItemResources>(itemModel) {


    override fun init(adapter : Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      parent : ViewGroup,
                      inflater : LayoutInflater,
                      resources : VideoItemResources?) : VideoItemViewHolder {
        return VideoItemViewHolder(
            parent = parent,
            itemView = inflater.inflate(
                layout,
                parent,
                false
            ),
            resources = resources
        )
    }


    override fun getLayout() : Int {
        return R.layout.item_video
    }


}