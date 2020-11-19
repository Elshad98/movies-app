package com.example.moviesapp.ui.popularmovie

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.App
import com.example.moviesapp.R
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_network_state.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val movieAdapter = PopularMoviePagedListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = spanSizeLookup

        rv_movie_list.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        viewModel = viewModel(MainActivityViewModel::class.java, App.scope())
        viewModel.moviePagedList.observe(this, Observer { movieAdapter.submitList(it) })
        viewModel.networkState.observe(this, stateObserve)
    }

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int {
            val viewType = movieAdapter.getItemViewType(position)
            return if (viewType == PopularMoviePagedListAdapter.MOVIE_VIEW_TYPE) 1 else 3
        }
    }

    private val stateObserve = Observer<NetworkState> { networkState ->
        progress_bar.visibility =
            if (viewModel.listIsEmpty() && networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
        error_message.visibility =
            if (viewModel.listIsEmpty() && networkState == NetworkState.ERROR) View.VISIBLE else View.GONE

        if (!viewModel.listIsEmpty()) {
            movieAdapter.setNetworkState(networkState)
        }
    }
}
