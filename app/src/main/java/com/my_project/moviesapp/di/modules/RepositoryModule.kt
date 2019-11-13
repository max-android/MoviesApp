package com.my_project.moviesapp.di.modules

import com.my_project.moviesapp.data.network.MoviesService
import com.my_project.moviesapp.data.repository.category_movies.CategoryMoviesRepository
import com.my_project.moviesapp.data.repository.main.MainRepository
import com.my_project.moviesapp.data.repository.movies.MoviesRepository
import com.my_project.moviesapp.data.repository.review.ReviewMovieRepository
import com.my_project.moviesapp.data.repository.video.VideoMovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository() = MainRepository()

    @Provides
    @Singleton
    fun provideMoviesRepository() = MoviesRepository()

    @Provides
    @Singleton
    fun provideCategoryMoviesRepository(api: MoviesService) = CategoryMoviesRepository(api)

    @Provides
    @Singleton
    fun provideVideoRepository(api: MoviesService) = VideoMovieRepository(api)

    @Provides
    @Singleton
    fun provideReviewMovieRepository(api: MoviesService) = ReviewMovieRepository(api)


}