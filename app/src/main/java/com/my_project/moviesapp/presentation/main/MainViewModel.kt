package com.my_project.moviesapp.presentation.main

import androidx.lifecycle.ViewModel
import com.my_project.moviesapp.App
import com.my_project.moviesapp.router.Router
import com.my_project.moviesapp.router.Screen
import javax.inject.Inject

/**
 * Created Максим on 03.11.2019.
 * Copyright © Max
 */
class MainViewModel : ViewModel() {

    @Inject
    lateinit var router: Router

    init {
        App.appComponent.inject(this)
    }

    fun showMovies() = router.replace(Screen.MOVIES)

    fun showActors() = router.replace(Screen.ACTORS)

}