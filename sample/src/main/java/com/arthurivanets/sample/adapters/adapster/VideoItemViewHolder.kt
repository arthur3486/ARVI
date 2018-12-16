/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurivanets.sample.adapters.adapster

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.arthurivanets.arvi.Config
import com.arthurivanets.arvi.adapster.AdapsterPlayableItemViewHolder
import com.arthurivanets.arvi.widget.PlaybackState
import com.arthurivanets.sample.R
import com.arthurivanets.sample.model.Video
import com.arthurivanets.sample.util.extensions.getColorCompat
import com.arthurivanets.sample.util.extensions.makeGone
import com.arthurivanets.sample.util.extensions.makeVisible
import com.arthurivanets.sample.util.extensions.setColor

class VideoItemViewHolder(
    parent : ViewGroup,
    itemView : View,
    private val resources : VideoItemResources?
) : AdapsterPlayableItemViewHolder<Video>(parent, itemView) {


    val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
    val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
    val errorIconIv = itemView.findViewById<ImageView>(R.id.errorIconIv)


    override fun bindData(data : Video?) {
        super.bindData(data)

        data?.let(::handleData)
    }


    private fun handleData(data : Video) {
        handleInfoViews()

        titleTv.text = data.title
    }


    private fun handleInfoViews() {
        progressBar.makeGone()
        progressBar.setColor(progressBar.context.getColorCompat(R.color.video_item_progress_bar_color))

        errorIconIv.makeGone()
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

        when(playbackState) {
            PlaybackState.BUFFERING -> onBufferingState()
            PlaybackState.READY -> onReadyState()
            PlaybackState.PAUSED -> onPausedState()
            PlaybackState.STOPPED -> onStoppedState()
            PlaybackState.ERROR -> onErrorState()
        }
    }


    private fun onBufferingState() {
        progressBar.makeVisible()
        errorIconIv.makeGone()
    }


    private fun onReadyState() {
        progressBar.makeGone()
        errorIconIv.makeGone()

        boundData?.isMuted?.let(::setMuted)
    }


    private fun onPausedState() {
        progressBar.makeGone()
    }


    private fun onStoppedState() {
        progressBar.makeGone()
    }


    private fun onErrorState() {
        progressBar.makeGone()
        errorIconIv.makeVisible()
    }


}