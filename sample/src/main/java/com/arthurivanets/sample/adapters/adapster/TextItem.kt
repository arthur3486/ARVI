package com.arthurivanets.sample.adapters.adapster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.sample.R

class TextItem(itemModel: String) : BaseItem<String, TextItemViewHolder, ItemResources>(itemModel) {

    override fun init(
        adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
        parent: ViewGroup,
        inflater: LayoutInflater,
        resources: ItemResources?
    ): TextItemViewHolder {
        return TextItemViewHolder(
            inflater.inflate(
                layout,
                parent,
                false
            )
        )
    }

    override fun getLayout(): Int {
        return R.layout.item_text
    }

}
