package com.example.moviesapp.data.vo

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("release_dates")
    val releaseDates: ReleaseDates,
    val revenue: Int,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        val backdropPath: String,
        val id: Int,
        val name: String,
        @SerializedName("poster_path")
        val posterPath: String
    )

    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String? = null,
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        val name: String
    )

    data class ReleaseDates(
        val results: List<Result>
    ) {
        data class Result(
            @SerializedName("iso_3166_1")
            val iso31661: String,
            @SerializedName("release_dates")
            val releaseDates: List<ReleaseDate>
        ) {
            data class ReleaseDate(
                val certification: String,
                @SerializedName("iso_639_1")
                val iso6391: String? = null,
                val note: String,
                @SerializedName("release_date")
                val releaseDate: String,
                val type: Int
            )
        }
    }

    data class SpokenLanguage(
        @SerializedName("iso_639_1")
        val iso6391: String,
        val name: String
    )
}