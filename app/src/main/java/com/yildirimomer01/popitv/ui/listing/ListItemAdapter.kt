package com.yildirimomer01.popitv.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.databinding.ItemCreatorBinding
import com.yildirimomer01.popitv.databinding.ListItemBinding
import com.yildirimomer01.popitv.module.GlideApp
import com.yildirimomer01.popitv.model.TvShow
import kotlinx.android.synthetic.main.list_item.view.*
import timber.log.Timber

class ListItemAdapter : PagingDataAdapter<TvShow, ListItemAdapter.ItemViewHolder>(COMPARATOR) {

    class ItemViewHolder(itemBinding:ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var bindingItem:  ListItemBinding = itemBinding
        companion object{
            fun from(itemBinding:ListItemBinding):ItemViewHolder{
                return ItemViewHolder(itemBinding)
            }
        }
        fun bind(tvShow:TvShow){
            bindingItem.tvshow = tvShow
            bindingItem.executePendingBindings();

            bindingItem.cardView.setOnClickListener{
                val directions= ListFragmentDirections.actionListFragmentToDetailFragment(tvShow.id!! )
                it.findNavController().navigate(directions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding : ListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.list_item, parent, false)
        return ItemViewHolder.from(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val tvShow =getItem(position)
        tvShow?.let { holder.bind(it) }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<TvShow>(){
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }

        }
    }
}