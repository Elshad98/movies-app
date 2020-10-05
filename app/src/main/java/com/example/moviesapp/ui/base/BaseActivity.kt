package com.example.moviesapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.factory.ToothpickFactory
import toothpick.Scope

open class BaseActivity : AppCompatActivity() {

    protected fun <T : ViewModel> viewModel(viewModelClass: Class<T>, scope: Scope): T {
        return ViewModelProvider(this, ToothpickFactory(scope))
            .get(viewModelClass)
    }
}