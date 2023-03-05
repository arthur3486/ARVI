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

package com.arthurivanets.sample.adapters.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arthurivanets.sample.ui.base.BaseFragment
import com.arthurivanets.sample.util.markers.CanHandleBackPressEvents

abstract class BaseViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager), CanHandleBackPressEvents {

    protected val fragmentManager: FragmentManager = fragmentManager
    protected val fragmentList = ArrayList<BaseFragment>()

    var pageTitleLookup: ((position: Int) -> CharSequence?)? = null

    fun addFragment(fragment: BaseFragment) {
        fragmentList.add(fragment)
    }

    fun getFragment(position: Int): BaseFragment? {
        return (if ((position >= 0) && (position < fragmentList.size)) fragmentList[position] else null)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return (pageTitleLookup?.invoke(position) ?: super.getPageTitle(position))
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun onBackPressed(): Boolean {
        for (fragment in fragmentList) {
            if (fragment.onBackPressed()) {
                return true
            }
        }

        return false
    }

}
