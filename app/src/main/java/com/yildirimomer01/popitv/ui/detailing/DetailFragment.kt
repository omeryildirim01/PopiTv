package com.yildirimomer01.popitv.ui.detailing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import com.yildirimomer01.popitv.module.GlideApp
import com.yildirimomer01.popitv.model.Status
import com.yildirimomer01.popitv.util.RatioImageView
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.layout_loading.*
import timber.log.Timber
import java.lang.StringBuilder

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var networkAdapter: NetworkAdapter
    private lateinit var creatorAdapter: CreatorAdapter
    private lateinit var dataBinding:FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = FragmentDetailBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        return dataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        networkAdapter = NetworkAdapter()
        rvNetwork.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
                adapter = networkAdapter
        }
        creatorAdapter = CreatorAdapter()
        rvCreator.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            adapter = creatorAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.tvShow.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS ->{
                    showLoading(false)
                    val tvShowDetail = it.data
                    dataBinding.tv = tvShowDetail
                    Timber.i(tvShowDetail.toString())
                    tvGenres.visibility =  if (tvShowDetail?.genres != null && tvShowDetail.genres.isNotEmpty())  View.VISIBLE else View.GONE
                    tVEpisode.visibility =  if (tvShowDetail?.numberOfEpisodes != null)  View.VISIBLE else View.GONE
                    tVSeason.visibility =  if (tvShowDetail?.numberOfSeasons != null) View.VISIBLE else View.GONE

                    val networks = tvShowDetail?.networks
                    if (networks != null && networks.isNotEmpty()){
                        networkAdapter.submitList(networks)
                    }
                    val creators = tvShowDetail?.createdBy
                    if (networks != null && networks.isNotEmpty()){
                        creatorAdapter.submitList(creators)
                    }
                }
                Status.ERROR ->{
                    showLoading(false)
                    Timber.i(it.toString())
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_LONG).show()
                    //onErrorState()
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