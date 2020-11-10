package com.example.moviesapp.ui.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.moviesapp.App
import com.example.moviesapp.BuildConfig.POSTER_BASE_URL
import com.example.moviesapp.R
import com.example.moviesapp.data.model.MovieDetails
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.ui.base.BaseActivity
import com.example.moviesapp.ui.personDetails.PersonDetailsActivity
import kotlinx.android.synthetic.main.activity_single_movie.*

class SingleMovieActivity : BaseActivity() {

    companion object {

        private const val TAG = "SingleMovieActivity"
        private const val MAX_SIZE_CAST = 15
    }

    private lateinit var viewModel: SingleMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("movie_id", 1)

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
        setCast(movieDetails)
        movie_title.text = movieDetails.title
        director.text = movieDetails.credits.director
        producer.text = movieDetails.credits.producer
        running_time.text = movieDetails.runningTime
        release_date.text = movieDetails.releaseDate
        mpaa_rating.text = movieDetails.releaseDates.mpaaRating
        storyline.text = movieDetails.overview
        user_score.text = movieDetails.rating.toString()

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

    private fun setCast(movieDetails: MovieDetails) {
        val inflater = LayoutInflater.from(cast.context)
        run loop@{
            movieDetails.credits.cast.forEachIndexed { index, item ->
                if (index == MAX_SIZE_CAST) return@loop
                cast.addView(
                    inflater.inflate(R.layout.item_movie_actor, cast, false).apply {
                        val movieActorURL = POSTER_BASE_URL + item.profilePath
                        val ivMovieActor = findViewById<ImageView>(R.id.iv_movie_actor)
                        Glide.with(this).load(movieActorURL).into(ivMovieActor)
                        findViewById<TextView>(R.id.actor_name).text = item.name
                        this.setOnClickListener {
                            val intent = Intent(context, PersonDetailsActivity::class.java)
                            intent.putExtra("person_id", item.id)
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}