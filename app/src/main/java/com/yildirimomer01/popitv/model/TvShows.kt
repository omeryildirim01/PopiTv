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
@JsonClass(generateAdapter = true)
data class TvShowDetail(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name="created_by")
    val createdBy:List<Creator>?,
    @Json(name="episode_run_time")
    val episodeRunTime:List<Int>,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "genres")
    val genres: List<Genre>?,
    @Json(name = "homepage")
    val homePage:String,
    val id: Long?,
    @Json(name = "in_production")
    val inProduction:Boolean,
    @Json(name = "languages")
    val languages:List<String>,
    @Json(name = "last_air_date")
    val lastAirDate:String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "networks")
    val networks:List<Network>?,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes:Int,
    @Json(name = "number_of_seasons")
    val numberOfSeasons:Int,
    @Json(name = "origin_country")
    val originCountry:List<String>?,
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
    @Json(name = "spoken_languages")
    val spokenLanguages:List<Language>?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "tagline")
    val tagLine: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Long?,
    var genreView:String?
){
    init {
        genreView = ""
    }
}
@JsonClass(generateAdapter = true)
data class Creator(
    val id: Long,
    @Json(name = "credit_id")
    val credit_id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "gender")
    val gender:Int?,
    @Json(name = "profile_path")
    val profilePath:String?

)
@JsonClass(generateAdapter = true)
data class Genre(
    val id: Long,
    val name: String?
)
@JsonClass(generateAdapter = true)
data class Network(
    @Json(name = "name")
    val name: String?,
    val id: Long,
    @Json(name = "logo_path")
    val logoPath:String?,
    @Json(name = "origin_country")
    val originCountry:String?
)
@JsonClass(generateAdapter = true)
data class Language(
    @Json(name = "english_name")
    val englishName: String?,
    @Json(name = "iso_639_1")
    val iso_639_1:String?,
    @Json(name = "name")
    val name:String?
)