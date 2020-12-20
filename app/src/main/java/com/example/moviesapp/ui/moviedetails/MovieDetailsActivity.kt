package com.example.moviesapp.ui.moviedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.moviesapp.App
import com.example.moviesapp.BuildConfig.*
import com.example.moviesapp.R
import com.example.moviesapp.data.model.MovieDetails
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.module.GlideApp
import com.example.moviesapp.ui.base.BaseActivity
import com.example.moviesapp.ui.persondetails.PersonDetailsActivity
import kotlinx.android.synthetic.main.activity_single_movie.*
import kotlinx.android.synthetic.main.view_network_state.*

class MovieDetailsActivity : BaseActivity() {

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra(INTENT_MOVIE_ID, 1)

        viewModel = viewModel(MovieDetailsViewModel::class.java, App.scope())
        viewModel.getMovieDetails(movieId).observe(this, Observer { bindUI(it) })
        viewModel.getNetworkState().observe(this, stateObserve)
    }

    private val stateObserve = Observer<NetworkState> { networkState ->
        progress_bar.visibility =
            if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
        error_message.visibility =
            if (networkState == NetworkState.ERROR) View.VISIBLE else View.GONE
    }

    private fun bindUI(movieDetails: MovieDetails) {
        if (movieDetails.similar.results.isEmpty()) {
            similar_movies_layout.visibility = View.GONE
        } else {
            setSimilarMovies(movieDetails)
        }
        if (movieDetails.credits.cast.isEmpty()) {
            cast_layout.visibility = View.GONE
        } else {
            setCast(movieDetails)
        }
        setGenres(movieDetails)
        setMovieInfo(movieDetails)

        if (movieDetails.videos.results.isNotEmpty()) {
            play_icon.visibility = View.VISIBLE
            play_icon.setOnClickListener {
                val url = VIDEO_BASE_URL + movieDetails.videos.results[0].key
                val ytPlay = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                val intent = Intent.createChooser(ytPlay, resources.getText(R.string.open_with))
                ytPlay.resolveActivity(packageManager)?.let {
                    startActivity(intent)
                }
            }
        }
        movieDetails.posterPath?.let { path ->
            val poster = POSTER_BASE_URL + path
            GlideApp.with(this).load(poster).into(iv_movie_poster)
            GlideApp.with(this).load(poster).into(cv_iv_movie_poster)
        }
    }

    private fun setMovieInfo(movieDetails: MovieDetails) {
        movie_title.text = movieDetails.title
        storyline.text = movieDetails.overview
        running_time.text = movieDetails.runningTime
        release_date.text = movieDetails.releaseDate
        director.text = movieDetails.credits.director
        producer.text = movieDetails.credits.producer
        user_score.text = movieDetails.rating.toString()
        mpaa_rating.text = movieDetails.releaseDates.mpaaRating
    }

    private fun setGenres(movieDetails: MovieDetails) {
        val inflater = LayoutInflater.from(genres.context)
        movieDetails.genres.forEach { genre ->
            genres.addView(
                inflater.inflate(R.layout.list_item_genre, genres, false).apply {
                    findViewById<TextView>(R.id.tag_label).text = genre.name
                }
            )
        }
    }

    private fun setSimilarMovies(movieDetails: MovieDetails) {
        val inflater = LayoutInflater.from(similar_movies.context)
        for ((index, item) in movieDetails.similar.results.withIndex()) {
            if (index == MAX_NUMBER_ITEM) break
            similar_movies.addView(
                inflater.inflate(R.layout.list_item_movie, similar_movies, false).apply {
                    item.posterPath?.let { path ->
                        val ivMoviePoster = findViewById<ImageView>(R.id.item_movie_poster)
                        GlideApp.with(this).load(POSTER_BASE_URL + path).into(ivMoviePoster)
                    }
                    findViewById<TextView>(R.id.item_movie_title).text = item.title
                    this.setOnClickListener {
                        val intent = Intent(context, MovieDetailsActivity::class.java)
                        intent.putExtra(INTENT_MOVIE_ID, item.id)
                        startActivity(intent)
                    }
                }
            )
        }
    }

    private fun setCast(movieDetails: MovieDetails) {
        val inflater = LayoutInflater.from(cast.context)
        for ((index, item) in movieDetails.credits.cast.withIndex()) {
            if (index == MAX_NUMBER_ITEM) break
            cast.addView(
                inflater.inflate(R.layout.list_item_actor, cast, false).apply {
                    item.profilePath?.let { path ->
                        val ivMovieActor = findViewById<ImageView>(R.id.iv_movie_actor)
                        GlideApp.with(this).load(POSTER_BASE_URL + path).into(ivMovieActor)
                    }
                    findViewById<TextView>(R.id.actor_name).text = item.name
                    this.setOnClickListener {
                        val intent = Intent(context, PersonDetailsActivity::class.java)
                        intent.putExtra(INTENT_PERSON_ID, item.id)
                        startActivity(intent)
                    }
                }
            )
        }
    }
}