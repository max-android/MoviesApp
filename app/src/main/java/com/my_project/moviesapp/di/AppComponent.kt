package com.my_project.moviesapp.di

import com.my_project.moviesapp.di.modules.*
import com.my_project.moviesapp.presentation.actors.ActorsFragment
import com.my_project.moviesapp.presentation.category_movies.CategoryMoviesFragment
import com.my_project.moviesapp.presentation.category_movies.CategoryMoviesViewModel
import com.my_project.moviesapp.presentation.main.MainActivity
import com.my_project.moviesapp.presentation.main.MainViewModel
import com.my_project.moviesapp.presentation.movies.MoviesFragment
import com.my_project.moviesapp.presentation.movies.MoviesViewModel
import com.my_project.moviesapp.presentation.review.ReviewMovieViewModel
import com.my_project.moviesapp.presentation.video.VideoMovieFragment
import com.my_project.moviesapp.presentation.video.VideoMovieViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Singleton
@Component
    (
    modules = [
        InterceptorModule::class,
        MovieNetworkModule::class,
        KinoNetworkModule::class,
        RepositoryModule::class,
        ProviderModule::class,
        RouterModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(moviesFragment: MoviesFragment)
    fun inject(categoryMoviesFragment: CategoryMoviesFragment)
    fun inject(actorsFragment: ActorsFragment)
    fun inject(mainViewModel: MainViewModel)
    fun inject(moviesViewModel: MoviesViewModel)
    fun inject(categoryMoviesViewModel: CategoryMoviesViewModel)
    fun inject(videoMovieViewModel: VideoMovieViewModel)
    fun inject(videoMovieFragment: VideoMovieFragment)
    fun inject(reviewMovieViewModel: ReviewMovieViewModel)
}