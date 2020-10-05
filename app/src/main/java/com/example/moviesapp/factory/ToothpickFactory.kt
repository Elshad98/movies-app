package com.example.moviesapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Scope

class ToothpickFactory(private val viewModelScope: Scope) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelScope.getInstance(modelClass)
    }
}