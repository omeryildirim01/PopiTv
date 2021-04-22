package com.yildirimomer01.popitv.ui.detailing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.model.Network
import com.yildirimomer01.popitv.module.GlideApp
import kotlinx.android.synthetic.main.item_network.view.*

class NetworkAdapter: ListAdapter<Network, NetworkAdapter.CastViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }
    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(network: Network) {

            itemView.apply {
                GlideApp.with(ivNetwork)
                    .load("https://image.tmdb.org/t/p/original${network.logoPath}")
                    .placeholder(R.drawable.ic_error_image)
                    .error(R.drawable.ic_error_image)
                    .transform(CircleCrop())
                    .into(ivNetwork)
                tvName.text = network.name
            }
        }
        companion object{
            fun from(parent: ViewGroup): CastViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemView = layoutInflater.inflate(R.layout.item_network,parent,false)
                return CastViewHolder(itemView)
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