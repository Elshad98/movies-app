package com.example.moviesapp.ui.personDetails

import android.os.Bundle
import android.util.Log
import com.example.moviesapp.R
import com.example.moviesapp.ui.base.BaseActivity

class PersonDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        val personId = intent.getIntExtra("person_id", 1)
        Log.d("PersonDetailsActivity", "Person id: $personId")
    }
}