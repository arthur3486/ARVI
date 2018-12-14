package com.arthurivanets.sample

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arthurivanets.arvi.Config
import com.arthurivanets.arvi.util.misc.ExoPlayerUtils
import com.arthurivanets.arvi.utils.recyclerview.PreCacheLinearLayoutManager
import com.arthurivanets.arvi.widget.PlayableItemsContainer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val VIDEOS = arrayListOf(
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
    )


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    private fun init() {
        recyclerView.autoplayMode = PlayableItemsContainer.AutoplayMode.MULTIPLE_SIMULTANEOUSLY
        recyclerView.setPlaybackTriggeringStates(
            PlayableItemsContainer.PlaybackTriggeringState.IDLING,
            PlayableItemsContainer.PlaybackTriggeringState.DRAGGING
        )
        recyclerView.layoutManager = PreCacheLinearLayoutManager(this).also {
            it.isItemPrefetchEnabled = true
            it.initialPrefetchItemCount = 4
        }
        recyclerView.adapter = VideoItemsRecyclerViewAdapter(
            this,
            getItems(),
            VideoItemResources(
                arviConfig = Config.Builder()
                    .cache(ExoPlayerUtils.getCache(this))
                    .build()
            )
        )
    }


    override fun onResume() {
        super.onResume()

        recyclerView.onResume()
    }


    override fun onPause() {
        super.onPause()

        recyclerView.onPause()
    }


    override fun onDestroy() {
        recyclerView?.onDestroy()

        super.onDestroy()
    }


    private fun getItems(count : Int = 100) : MutableList<VideoItem> {
        val items = ArrayList<VideoItem>()

        for(i in 0 until count) {
            val index = (i % VIDEOS.size)
            val url = VIDEOS[index]
            val isMuted = (index != VIDEOS.lastIndex)

            items.add(VideoItem(Video(
                title = (Uri.parse(url).lastPathSegment ?: ""),
                videoUrl = url,
                isMuted = isMuted
            )))
        }

        return items
    }


}
