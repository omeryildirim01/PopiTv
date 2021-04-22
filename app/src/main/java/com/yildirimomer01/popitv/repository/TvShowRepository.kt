package com.yildirimomer01.popitv.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.yildirimomer01.popitv.model.TvShow
import com.yildirimomer01.popitv.model.TvShowPagingSource
import com.yildirimomer01.popitv.network.TvShowService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRepository @Inject constructor(
    private val tvShowService: TvShowService,
    private val tvShowPagingSource: TvShowPagingSource
){
    fun getPopularTvShows(): Flowable<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {tvShowPagingSource}
        ).flowable
    }
    fun getTvShowDetails(tvId: Long): Single<TvShow> {
        return tvShowService.getTvShow(tvId)
            .subscribeOn(Schedulers.io())
    }
}