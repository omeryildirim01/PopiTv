package com.yildirimomer01.popitv.network

import com.yildirimomer01.popitv.model.TvShow
import com.yildirimomer01.popitv.model.TvShowDetail
import com.yildirimomer01.popitv.model.TvShows
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("tv/popular")
    fun getPopularTvShows(@Query("page") page: Int): Single<TvShows>

    @GET("tv/{tv_id}")
    fun getTvShow(@Path("tv_id")tvId: Long): Single<TvShowDetail>
}