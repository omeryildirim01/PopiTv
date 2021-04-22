package com.yildirimomer01.popitv.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShows (
    val page: Long,
    @Json(name = "total_pages")
    val totalPages: Long,
    @Json(name = "total_results")
    val totalResults: Long,
    val results:List<TvShow>
)
@JsonClass(generateAdapter = true)
data class TvShow(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "genre_ids")
    val genres: List<Long>?,
    val id: Long?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    @Json(name = "original_language")
    val originalLanguage:String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Long?,
)
