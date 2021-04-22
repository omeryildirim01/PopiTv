package com.yildirimomer01.popitv.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yildirimomer01.popitv.R
import kotlinx.android.synthetic.main.item_footer_state.view.*

class ItemFooterStateAdapter (private val retry:() ->Unit) : LoadStateAdapter<ItemFooterStateAdapter.ItemFooterStateViewHolder>(){

    override fun onBindViewHolder(holder: ItemFooterStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemFooterStateViewHolder {
        return ItemFooterStateViewHolder.create(parent,retry)
    }

    class ItemFooterStateViewHolder private constructor(
        itemView: View,
        retry: () -> Unit

    ): RecyclerView.ViewHolder(itemView){

        companion object{
            fun create(parent: ViewGroup, retry: () -> Unit) : ItemFooterStateViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_footer_state,parent,false)
                return ItemFooterStateViewHolder(view,retry)
            }

        }
        init {
            itemView.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState) {
            itemView.apply {
                if (loadState is LoadState.Error ){
                    tvErrorMessage.text = loadState.error.message
                }
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvErrorMessage.isVisible = loadState !is LoadState.Loading
            }

        }
    }
}