package com.yildirimomer01.popitv.ui.detailing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yildirimomer01.popitv.R
import dagger.hilt.android.AndroidEntryPoint
import com.yildirimomer01.popitv.module.GlideApp
import com.yildirimomer01.popitv.model.Status
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.layout_loading.*
import timber.log.Timber
import java.lang.StringBuilder

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var networkAdapter: NetworkAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        networkAdapter = NetworkAdapter()
        rvCast.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
                adapter = networkAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.tvShow.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS ->{
                    showLoading(false)
                    val tvShowDetail = it.data
                    Timber.i(tvShowDetail.toString())
                    GlideApp.with(ivBackdrop)
                        .load("https://image.tmdb.org/t/p/original${tvShowDetail?.backdropPath}")
                        .placeholder(R.drawable.ic_image_placeholder)
                        .error(R.drawable.ic_error_image)
                        .into(ivBackdrop)
                    GlideApp.with(ivPoster)
                        .load("https://image.tmdb.org/t/p/w500${tvShowDetail?.posterPath}")
                        .placeholder(R.drawable.ic_image_placeholder)
                        .error(R.drawable.ic_error_image)
                        .into(ivPoster)

                    tvTitle.text = tvShowDetail?.name
                    if (tvShowDetail?.genres != null && tvShowDetail.genres.isNotEmpty()){
                        val genres = tvShowDetail.genres.joinToString (
                            separator = " | ",
                            transform = {genre ->  genre.name!! }
                        )
                        tvGenres.text = genres

                    }else{
                        tvGenres.visibility = View.GONE
                    }
                    val strBuilder = StringBuilder()
                    if (tvShowDetail?.firstAirDate != null){
                       strBuilder.append(tvShowDetail.firstAirDate)
                    }
                    if (tvShowDetail?.lastAirDate != null){
                        strBuilder.append(" / ")
                        strBuilder.append(tvShowDetail.lastAirDate)
                    }
                    if (strBuilder.toString().isNotEmpty() ){
                        tVAirDate.text = strBuilder.toString()
                    }

                    if (tvShowDetail?.numberOfEpisodes != null){
                        tVEpisode.text = tvShowDetail?.numberOfEpisodes.toString()
                    }else{
                        tVEpisode.visibility = View.GONE
                    }
                    if (tvShowDetail?.numberOfSeasons != null){
                        tVSeason.text = tvShowDetail?.numberOfSeasons.toString()
                    }else{
                        tVSeason.visibility = View.GONE
                    }

                    tvShowDetail?.voteAverage?.let { rating->
                        rbRating.rating = (rating/2).toFloat()
                    }
                    tvVoteCount.text = tvShowDetail?.voteCount.toString()
                    tVOverivew.text = tvShowDetail?.overview

                    val networks = tvShowDetail?.networks
                    if (networks != null && networks.isNotEmpty()){
                        networkAdapter.submitList(networks)
                    }

                }
                Status.ERROR ->{
                    showLoading(false)
                    Timber.i(it.toString())
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_LONG).show()
                    onErrorState()
                }
                Status.LOADING ->{
                    Timber.i("loading .. ")
                    showLoading(true)
                }
            }
        })
    }
    private fun showLoading(isShow: Boolean){
        loadingContainer.visibility = if (isShow) View.VISIBLE else View.GONE
    }
    private fun onErrorState(){
        this.view?.findNavController()?.popBackStack()
    }
}