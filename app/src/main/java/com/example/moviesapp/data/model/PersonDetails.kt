package com.example.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class PersonDetails(
    val adult: Boolean,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: String? = null,
    val gender: Int,
    val homepage: String? = null,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("movie_credits")
    val movieCredits: MovieCredits,
    val name: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String
) {
    data class MovieCredits(
        val cast: List<Cast>,
        val crew: List<Crew> = emptyList()

    ) {
        data class Cast(
            val adult: Boolean,
            @SerializedName("backdrop_path")
            val backdropPath: String,
            val character: String,
            @SerializedName("credit_id")
            val creditId: String,
            @SerializedName("genre_ids")
            val genreIds: List<Int>,
            val id: Int,
            @SerializedName("original_language")
            val originalLanguage: String,
            @SerializedName("original_title")
            val originalTitle: String,
            val overview: String,
            val popularity: Double,
            @SerializedName("poster_path")
            val posterPath: String,
            @SerializedName("release_date")
            val releaseDate: String,
            val title: String,
            val video: Boolean,
            @SerializedName("vote_average")
            val voteAverage: Double,
            @SerializedName("vote_count")
            val voteCount: Int
        )

        data class Crew(
            val adult: Boolean,
            @SerializedName("backdrop_path")
            val backdropPath: String,
            @SerializedName("credit_id")
            val creditId: String,
            val department: String,
            @SerializedName("genre_ids")
            val genreIds: List<Int>,
            val id: Int,
            val job: String,
            @SerializedName("original_language")
            val originalLanguage: String,
            @SerializedName("original_title")
            val originalTitle: String,
            val overview: String,
            val popularity: Double,
            @SerializedName("poster_path")
            val posterPath: String,
            @SerializedName("release_date")
            val releaseDate: String,
            val title: String,
            val video: Boolean,
            @SerializedName("vote_average")
            val voteAverage: Double,
            @SerializedName("vote_count")
            val voteCount: Int
        )
    }
}