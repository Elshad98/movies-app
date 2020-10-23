package com.example.moviesapp.data.vo

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

data class MovieDetails(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Int,
    val credits: Credits,
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
    private val _releaseDate: String,
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
    val releaseDate: String
        get() {
            val date = LocalDate.parse(_releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("en")))
        }

    val runningTime: String
        get() {
            val hour = runtime / 60
            val min = runtime % 60
            return "${hour}h ${if (min > 9) min else "0$min"}min"
        }

    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        val backdropPath: String,
        val id: Int,
        val name: String,
        @SerializedName("poster_path")
        val posterPath: String
    )

    data class Credits(
        val cast: List<Cast>,
        val crew: List<Crew>
    ) {
        val director: String
            get() = getNameByJob("Director")

        val producer: String
            get() = getNameByJob("Producer")

        private fun getNameByJob(job: String): String {
            var result = ""
            for (item in crew) {
                if (item.job == job) {
                    result = item.name
                    break
                }
            }
            return result
        }

        data class Cast(
            @SerializedName("cast_id")
            val castId: Int,
            val character: String,
            @SerializedName("credit_id")
            val creditId: String,
            val gender: Int,
            val id: Int,
            val name: String,
            val order: Int,
            @SerializedName("profile_path")
            val profilePath: String
        )

        data class Crew(
            @SerializedName("credit_id")
            val creditId: String,
            val department: String,
            val gender: Int,
            val id: Int,
            val job: String,
            val name: String,
            @SerializedName("profile_path")
            val profilePath: String? = null
        )
    }

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
        val mpaaRating: String
            get() {
                var result = ""
                for (item in results) {
                    if (item.iso31661 == "US") {
                        result = item.releaseDates[0].certification
                        break
                    }
                }
                return result
            }

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