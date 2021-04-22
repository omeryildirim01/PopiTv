package com.yildirimomer01.popitv.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.module.GlideApp
import com.yildirimomer01.popitv.model.TvShow
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.list_item.view.*
import timber.log.Timber

class ListItemAdapter : PagingDataAdapter<TvShow, ListItemAdapter.ItemViewHolder>(COMPARATOR) {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        companion object{
            fun from(parent: ViewGroup):ItemViewHolder{

                try {
                    val inflater = LayoutInflater.from(parent.context)
                    val itemView = inflater.inflate(R.layout.list_item,parent,false)
                    return ItemViewHolder(itemView = itemView)
                }catch (ex:Throwable){
                    Timber.e(ex)
                    return ItemViewHolder(itemView = View(parent.context))
                }
            }
        }
        fun bind(tvShow:TvShow){
            itemView.setOnClickListener{
                val directions= ListFragmentDirections.actionListFragmentToDetailFragment(tvShow.id!! )
                it.findNavController().navigate(directions)
            }
            itemView.apply {
                GlideApp.with(ivPoster)
                    .load("https://image.tmdb.org/t/p/w500${tvShow.posterPath}")
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error_image)
                    .into(ivPoster)
                tVTitle.text = tvShow.originalName
                tVReleaseDate.text = tvShow.firstAirDate
                tvShow?.voteAverage?.let { rating->
                    rbRating.rating = (rating/2).toFloat()
                }
                tvVoteCount.text = tvShow?.voteCount.toString()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
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