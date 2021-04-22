package com.yildirimomer01.popitv.ui.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.yildirimomer01.popitv.model.TvShow
import com.yildirimomer01.popitv.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _popularTvShows = MutableLiveData<PagingData<TvShow>>()
    val popularTvShows: LiveData<PagingData<TvShow>>
        get() = _popularTvShows

    init {
        getTvShows()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    fun onRefresh(){
        getTvShows()
    }
    private fun getTvShows(){
        compositeDisposable.add(
            tvShowRepository.getPopularTvShows()
                .cachedIn(viewModelScope)
                .subscribe {
                    _popularTvShows.value = it
                }
        )
    }
}