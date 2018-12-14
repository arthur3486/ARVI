package com.arthurivanets.sample

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arthurivanets.arvi.Config
import com.arthurivanets.arvi.adapster.AdapsterPlayableItemViewHolder
import com.arthurivanets.arvi.widget.PlaybackState

class VideoItemViewHolder(
    parent : ViewGroup,
    itemView : View,
    private val resources : VideoItemResources?
) : AdapsterPlayableItemViewHolder<Video>(parent, itemView) {


    val playbackStateTv = itemView.findViewById<TextView>(R.id.playbackStateTv)
    val titleTv = itemView.findViewById<TextView>(R.id.titleTv)


    override fun bindData(data : Video?) {
        super.bindData(data)

        titleTv.text = (data?.title ?: "")

        isMuted = (data?.isMuted ?: false)
    }


    override fun getUrl() : String {
        return (boundData?.videoUrl ?: "")
    }


    override fun getConfig() : Config {
        return (resources?.arviConfig ?: super.getConfig())
    }


    override fun isLooping() : Boolean {
        return true
    }


    override fun onStateChanged(playbackState : PlaybackState) {
        super.onStateChanged(playbackState)

        playbackStateTv.text = playbackState.toString()
    }


}