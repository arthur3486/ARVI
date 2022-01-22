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

package com.arthurivanets.sample.adapters.basic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.arvi.Config
import com.arthurivanets.sample.R
import com.arthurivanets.sample.model.Video

class BasicVideoItemsRecyclerViewAdapter(
    context : Context,
    private val items : MutableList<Video>,
    private val arviConfig : Config
) : RecyclerView.Adapter<BasicVideoItemViewHolder>() {


    private val inflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BasicVideoItemViewHolder {
        return BasicVideoItemViewHolder(
            parent = parent,
            itemView = inflater.inflate(
                R.layout.item_video,
                parent,
                false
            ),
            arviConfig = arviConfig
        )
    }


    override fun onBindViewHolder(holder : BasicVideoItemViewHolder, position : Int) {
        holder.bindData(getItem(position))
    }


    fun getItem(position : Int) : Video? {
        return (if((position >= 0) && (position < itemCount)) items[position] else null)
    }


    override fun getItemCount() : Int {
        return items.size
    }


}