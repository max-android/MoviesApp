package com.my_project.moviesapp.presentation.movies

import androidx.lifecycle.ViewModel
import com.my_project.moviesapp.App
import com.my_project.moviesapp.data.repository.movies.MoviesRepository
import javax.inject.Inject

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class MoviesViewModel:ViewModel() {

    @Inject
    lateinit var mRepository: MoviesRepository

    init {
        App.appComponent.inject(this)
    }

}