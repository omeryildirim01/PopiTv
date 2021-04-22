package com.yildirimomer01.popitv.ui.listing

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.yildirimomer01.popitv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_error.*

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    lateinit var itemAdapter: ListItemAdapter
    private val listViewModel by viewModels<ListViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ListItemAdapter()

        rvTvShow.layoutManager = LinearLayoutManager(requireContext())
        rvTvShow.adapter = itemAdapter.withLoadStateFooter(
            ItemFooterStateAdapter{
                itemAdapter.retry()
            }
        )

        itemAdapter.addLoadStateListener {loadState->
            srLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
            llErroContainer.isVisible = loadState.source.refresh is LoadState.Error
            rvTvShow.isVisible = !llErroContainer.isVisible
            if (loadState.source.refresh is LoadState.Error){
                btnRetry.setOnClickListener{
                    itemAdapter.retry()
                }
                llErroContainer.isVisible = loadState.source.refresh is LoadState.Error
                val errorMessage = (loadState.source.refresh as LoadState.Error).error.message
                tvErrorMessage.text = errorMessage
            }
        }
        srLayout.setOnRefreshListener {
            listViewModel.onRefresh()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listViewModel.popularTvShows.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitData(lifecycle,it)
        })
    }
}