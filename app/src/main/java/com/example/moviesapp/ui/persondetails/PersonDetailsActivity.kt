package com.example.moviesapp.ui.persondetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.moviesapp.App
import com.example.moviesapp.BuildConfig.*
import com.example.moviesapp.R
import com.example.moviesapp.data.model.PersonDetails
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.module.GlideApp
import com.example.moviesapp.ui.base.BaseActivity
import com.example.moviesapp.ui.moviedetails.SingleMovieActivity
import kotlinx.android.synthetic.main.activity_person_details.*
import kotlinx.android.synthetic.main.view_network_state.*

class PersonDetailsActivity : BaseActivity() {

    companion object {

        private const val MAX_NUMBER_ITEM = 15
    }

    private lateinit var viewModel: PersonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        val personId = intent.getIntExtra(INTENT_PERSON_ID, 1)

        viewModel = viewModel(PersonDetailsViewModel::class.java, App.scope())
        viewModel.getPersonDetails(personId).observe(this, Observer { bindUI(it) })
        viewModel.getNetworkState().observe(this, stateObserver)
    }

    private val stateObserver = Observer<NetworkState> { networkState ->
        progress_bar.visibility =
            if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
        error_message.visibility =
            if (networkState == NetworkState.ERROR) View.VISIBLE else View.GONE
    }

    private fun bindUI(personDetails: PersonDetails) {
        person_name.text = personDetails.name
        date_of_birth.text = personDetails.dateAndPlaceOfBirth
        if (personDetails.biography.isEmpty()) {
            biography_person_layout.visibility = View.GONE
        } else {
            biography.text = personDetails.biography
        }
        if (personDetails.movieCredits.cast.isEmpty()) {
            filmography_person_layout.visibility = View.GONE
        } else {
            setFilmography(personDetails)
        }
        personDetails.profilePath?.let {
            GlideApp.with(this).load(POSTER_BASE_URL + it).into(iv_person_image)
        }
    }

    private fun setFilmography(personDetails: PersonDetails) {
        val inflater = LayoutInflater.from(filmography.context)
        for ((index, item) in personDetails.movieCredits.cast.withIndex()) {
            if (index == MAX_NUMBER_ITEM) break
            filmography.addView(
                inflater.inflate(R.layout.list_item_movie, filmography, false).apply {
                    item.posterPath?.let { path ->
                        val ivMoviePoster = findViewById<ImageView>(R.id.item_movie_poster)
                        GlideApp.with(this).load(POSTER_BASE_URL + path).into(ivMoviePoster)
                    }
                    findViewById<TextView>(R.id.item_movie_title).text = item.title
                    setOnClickListener {
                        val intent = Intent(context, SingleMovieActivity::class.java)
                        intent.putExtra(INTENT_MOVIE_ID, item.id)
                        startActivity(intent)
                    }
                }
            )
        }
    }
}