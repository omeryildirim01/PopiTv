package com.yildirimomer01.popitv.ui.detailing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.model.Creator
import com.yildirimomer01.popitv.module.GlideApp
import kotlinx.android.synthetic.main.item_creator.view.*

class CreatorAdapter : ListAdapter<Creator, CreatorAdapter.ItemViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(creator: Creator) {
            itemView.apply {
                GlideApp.with(ivCreator)
                    .load("https://image.tmdb.org/t/p/original${creator.profilePath}")
                    .placeholder(R.drawable.ic_error_image)
                    .error(R.drawable.ic_error_image)
                    .transform(CircleCrop())
                    .into(ivCreator)
                tvName.text = creator.name
            }
        }
        companion object{
            fun from(parent: ViewGroup): ItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemView = layoutInflater.inflate(R.layout.item_creator,parent,false)
                return ItemViewHolder(itemView)
            }
        }
    }

    companion object{
        private val COMPARATOR = object  : DiffUtil.ItemCallback<Creator>() {
            override fun areItemsTheSame(oldItem: Creator, newItem: Creator) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Creator, newItem: Creator) = oldItem == newItem
        }
    }
}