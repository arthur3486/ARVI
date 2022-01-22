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

@file:JvmName("GeneralExtensions")

package com.arthurivanets.sample.util.extensions

import android.content.Context
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.arthurivanets.sample.adapters.base.BaseViewPagerAdapter
import com.arthurivanets.sample.util.markers.CanManagePlayback
import com.arthurivanets.sample.util.markers.HasTitle


fun BaseViewPagerAdapter.startPlaybackIfNecessary() {
    for(i in 0 until this.count) {
        this.getFragment(i)?.ifCanManagePlayback { startPlayback() }
    }
}


fun BaseViewPagerAdapter.stopPlaybackIfNecessary() {
    for(i in 0 until this.count) {
        this.getFragment(i)?.ifCanManagePlayback { stopPlayback() }
    }
}


fun Any.getTitleOrDefault(context : Context, defaultTitle : CharSequence) : CharSequence {
    return (if(this is HasTitle) this.getTitle(context) else defaultTitle)
}


inline fun PagerAdapter.ifFragmentPagerAdapter(action : FragmentPagerAdapter.() -> Unit) {
    if(this is FragmentPagerAdapter) {
        action(this)
    }
}


inline fun Any.ifCanManagePlayback(action : CanManagePlayback.() -> Unit) {
    if(this is CanManagePlayback) {
        action(this)
    }
}