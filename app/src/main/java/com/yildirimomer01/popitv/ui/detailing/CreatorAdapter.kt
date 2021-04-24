package com.yildirimomer01.popitv.ui.detailing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.databinding.ItemCreatorBinding
import com.yildirimomer01.popitv.model.Creator


class CreatorAdapter : ListAdapter<Creator, CreatorAdapter.ItemViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding : ItemCreatorBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_creator, parent, false)
        return ItemViewHolder.from(binding)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
    class ItemViewHolder(itemBinding: ItemCreatorBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var bindingItem: ItemCreatorBinding = itemBinding
        fun bind(creator: Creator) {
            bindingItem.creator = creator
            bindingItem.executePendingBindings();
        }
        companion object{
            fun from(binding : ItemCreatorBinding): ItemViewHolder{
                return ItemViewHolder(binding)
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