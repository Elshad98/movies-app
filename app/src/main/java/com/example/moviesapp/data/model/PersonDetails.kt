package com.example.moviesapp.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class PersonDetails(
    val adult: Boolean,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String? = null,
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
    val placeOfBirth: String? = null,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String? = null
) {

    val dateAndPlaceOfBirth: String
        get() {
            var date: String? = null
            birthday?.let {
                date = with(LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))) {
                    format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale("en")))
                }
            }
            return "${date ?: ""} ${if (placeOfBirth == null) "" else "in $placeOfBirth" }"
        }

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
            val posterPath: String? = null,
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