package com.yildirimomer01.popitv.model

import javax.inject.Inject
import javax.inject.Singleton
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.yildirimomer01.popitv.network.TvShowService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

@Singleton
class TvShowPagingSource @Inject constructor(
    private val tvShowService: TvShowService
) : RxPagingSource<Int, TvShow>() {
    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state?.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TvShow>> {
        val page=  params.key ?: 1
        return  tvShowService.getPopularTvShows(page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (page == 1) null else page-1,
                    nextKey = if (page == it.totalPages.toInt()) null else it.page.toInt() + 1
                ) as LoadResult<Int,TvShow>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}