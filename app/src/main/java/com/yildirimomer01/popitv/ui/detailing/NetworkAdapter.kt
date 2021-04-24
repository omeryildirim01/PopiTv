package com.yildirimomer01.popitv.ui.detailing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.databinding.ItemNetworkBinding
import com.yildirimomer01.popitv.model.Network

class NetworkAdapter: ListAdapter<Network, NetworkAdapter.ItemViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding : ItemNetworkBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_network, parent, false)
        return ItemViewHolder.from(binding)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
    class ItemViewHolder(itemBinding: ItemNetworkBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var bindingItem: ItemNetworkBinding = itemBinding
        fun bind(network: Network) {
            bindingItem.network = network
            bindingItem.executePendingBindings();
        }
        companion object{
            fun from(binding : ItemNetworkBinding): ItemViewHolder{
                return ItemViewHolder(binding)
            }
        }
    }

    companion object{
        private val COMPARATOR = object  : DiffUtil.ItemCallback<Network>() {
            override fun areItemsTheSame(oldItem: Network, newItem: Network) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Network, newItem: Network) = oldItem == newItem
        }
    }
}