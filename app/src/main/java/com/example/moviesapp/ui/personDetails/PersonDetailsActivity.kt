package com.example.moviesapp.ui.personDetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.moviesapp.App
import com.example.moviesapp.BuildConfig.POSTER_BASE_URL
import com.example.moviesapp.R
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.model.PersonDetails
import com.example.moviesapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_person_details.*

class PersonDetailsActivity : BaseActivity() {

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

        val personImageURL = POSTER_BASE_URL + personDetails.profilePath
        Glide.with(this).load(personImageURL).into(iv_person_image)
    }
}