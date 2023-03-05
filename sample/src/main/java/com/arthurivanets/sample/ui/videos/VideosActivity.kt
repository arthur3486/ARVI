/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.work@gmail.com
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

package com.arthurivanets.sample.ui.videos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.arthurivanets.arvi.widget.PlayableItemsContainer
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.base.BaseViewPagerAdapter
import com.arthurivanets.sample.adapters.videos.VideosViewPagerAdapter
import com.arthurivanets.sample.ui.adapster.AdapsterVideosFragment
import com.arthurivanets.sample.ui.base.BaseActivity
import com.arthurivanets.sample.ui.basic.BasicVideosFragment
import com.arthurivanets.sample.util.extensions.getColorCompat
import com.arthurivanets.sample.util.extensions.getTitleOrDefault
import com.arthurivanets.sample.util.listeners.ArviViewPagerPlaybackController
import kotlinx.android.synthetic.main.activity_videos.*
import kotlinx.android.synthetic.main.view_top_bar.*

class VideosActivity : BaseActivity() {

    private var type = Type.BASIC

    private lateinit var adapter: VideosViewPagerAdapter

    companion object {

        const val TAG = "VideosActivity"
        private const val EXTRA_TYPE = "type"

        @JvmStatic
        fun newBasicInstance(context: Context): Intent {
            return newInstance(context, Type.BASIC)
        }

        @JvmStatic
        fun newAdapsterInstance(context: Context): Intent {
            return newInstance(context, Type.ADAPSTER)
        }

        @JvmStatic
        fun newInstance(context: Context, type: Type): Intent {
            return Intent(context, VideosActivity::class.java)
                .putExtras(newExtrasBundle(type))
        }

        @JvmStatic
        fun newExtrasBundle(type: Type): Bundle {
            return bundleOf(EXTRA_TYPE to type)
        }

    }

    override fun fetchExtras(extras: Bundle) {
        super.fetchExtras(extras)

        type = ((extras.getSerializable(EXTRA_TYPE) as Type?) ?: type)
    }

    override fun init(savedInstanceState: Bundle?) {
        initTopBar()
        initViewPager()
        initTabLayout()
    }

    private fun initTopBar() {
        top_bar_back_button.setOnClickListener { onBackPressed() }
        top_bar_title.text = type.getTitle(this)
    }

    private fun initViewPager() {
        with(viewPager) {
            addOnPageChangeListener(ArviViewPagerPlaybackController(viewPager))

            adapter = initAdapter()
            offscreenPageLimit = (adapter?.count ?: 0)
        }
    }

    private fun initAdapter(): VideosViewPagerAdapter {
        return VideosViewPagerAdapter(supportFragmentManager).apply {
            populateAdapter()

            pageTitleLookup = { getFragment(it)?.getTitleOrDefault(this@VideosActivity, "") }
            adapter = this
        }
    }

    private fun BaseViewPagerAdapter.populateAdapter() {
        when (type) {
            Type.BASIC -> {
                addFragment(BasicVideosFragment.newInstance(PlayableItemsContainer.AutoplayMode.ONE_AT_A_TIME))
                addFragment(BasicVideosFragment.newInstance(PlayableItemsContainer.AutoplayMode.MULTIPLE_SIMULTANEOUSLY))
            }
            Type.ADAPSTER -> {
                addFragment(AdapsterVideosFragment.newInstance(PlayableItemsContainer.AutoplayMode.ONE_AT_A_TIME))
                addFragment(AdapsterVideosFragment.newInstance(PlayableItemsContainer.AutoplayMode.MULTIPLE_SIMULTANEOUSLY))
            }
        }
    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setSelectedTabIndicatorColor(getColorCompat(R.color.colorAccent))
        initTabLayoutTabs()
        tabLayout.getTabAt(0)?.select()
    }

    private fun initTabLayoutTabs() {
        with(tabLayout) {
            getTabAt(0)?.text = getString(R.string.title_videos_fragment_playback_one_at_a_time)
            getTabAt(1)?.text = getString(R.string.title_videos_fragment_playback_multiple_simultaneously)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_videos
    }

}
