package com.example.moviesapp.ui.singlemoviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.moviesapp.App
import com.example.moviesapp.BuildConfig.POSTER_BASE_URL
import com.example.moviesapp.R
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.MovieDetails
import com.example.moviesapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_single_movie.*

class SingleMovieActivity : BaseActivity() {

    private lateinit var viewModel: SingleMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)

        viewModel = viewModel(SingleMovieViewModel::class.java, App.scope())
        viewModel.getMovieDetails(movieId).observe(this, Observer { bindUI(it) })
        viewModel.getNetworkState().observe(this, stateObserve)
    }

    private val stateObserve = Observer<NetworkState> { networkState ->
        progress_bar.visibility =
            if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
        txt_error.visibility = if (networkState == NetworkState.ERROR) View.VISIBLE else View.GONE
    }

    private fun bindUI(movieDetails: MovieDetails) {
        setGenres(movieDetails)
        movie_title.text = movieDetails.title
        director.text = movieDetails.credits.director
        producer.text = movieDetails.credits.producer
        running_time.text = movieDetails.runningTime
        release_date.text = movieDetails.releaseDate
        mpaa_rating.text = movieDetails.releaseDates.mpaaRating

        val moviePosterURL = POSTER_BASE_URL + movieDetails.posterPath
        val glide = Glide.with(this).load(moviePosterURL)

        glide.into(iv_movie_poster)
        glide.into(cv_iv_movie_poster)
    }

    private fun setGenres(movieDetails: MovieDetails) {
        val inflater = LayoutInflater.from(genres.context)
        movieDetails.genres.forEach { genre ->
            genres.addView(
                inflater.inflate(R.layout.item_movie_genre, genres, false).apply {
                    findViewById<TextView>(R.id.tag_label).apply {
                        text = genre.name
                    }
                }
            )
        }
    }
}