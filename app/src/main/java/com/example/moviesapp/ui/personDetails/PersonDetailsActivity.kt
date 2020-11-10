package com.example.moviesapp.ui.personDetails

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
import com.example.moviesapp.data.model.PersonDetails
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.ui.base.BaseActivity
import com.example.moviesapp.ui.moviedetails.SingleMovieActivity
import kotlinx.android.synthetic.main.activity_person_details.*

class PersonDetailsActivity : BaseActivity() {

    companion object {

        private const val MAX_SIZE_CAST = 15
    }

    private lateinit var viewModel: PersonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        val personId = intent.getIntExtra("person_id", 1)

        viewModel = viewModel(PersonDetailsViewModel::class.java, App.scope())
        viewModel.getPersonDetails(personId).observe(this, Observer { bindUI(it) })
        viewModel.getNetworkState().observe(this, stateObserver)
    }

    private val stateObserver = Observer<NetworkState> { networkState ->
        progress_bar_person.visibility =
            if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
        txt_error_person.visibility =
            if (networkState == NetworkState.ERROR) View.VISIBLE else View.GONE
    }

    private fun bindUI(personDetails: PersonDetails) {
        person_name.text = personDetails.name
        date_of_birth.text = personDetails.dateAndPlaceOfBirth

        setFilmography(personDetails)
        val personImageURL = POSTER_BASE_URL + personDetails.profilePath
        Glide.with(this).load(personImageURL).into(iv_person_image)
    }

    private fun setFilmography(personDetails: PersonDetails) {
        val inflater = LayoutInflater.from(filmography.context)
        run loop@{
            personDetails.movieCredits.cast.forEachIndexed { index, item ->
                if (index == MAX_SIZE_CAST) return@loop
                filmography.addView(
                    inflater.inflate(R.layout.movie_list_item, filmography, false).apply {
                        val poster = POSTER_BASE_URL + item.posterPath
                        val ivMoviePoster = findViewById<ImageView>(R.id.cv_iv_movie_poster)
                        Glide.with(this).load(poster).into(ivMoviePoster)
                        findViewById<TextView>(R.id.cv_movie_title).apply {
                            text = item.title
                        }
                        setOnClickListener {
                            val intent = Intent(context, SingleMovieActivity::class.java)
                            intent.putExtra("movie_id", item.id)
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}