package com.my_project.moviesapp.data.repository.category_movies

import com.my_project.moviesapp.data.mapper.CategoryMapper
import com.my_project.moviesapp.data.network.MoviesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class CategoryMoviesRepository @Inject constructor(private val api: MoviesService) {

    fun nowPlayingMovies(type:String) = api.nowPlayingMovies()
        .map { CategoryMapper.convertToCategory(type,it) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun popularMovies(type:String,page:Int) = api.popularMovies(page.toString())
        .map { CategoryMapper.convertToCategory(type,it) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun topRatedMovies(type:String,page:Int) = api.topRatedMovies(page.toString())
        .map { CategoryMapper.convertToCategory(type,it) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun upcomingMovies(type:String) = api.upcomingMovies()
        .map { CategoryMapper.convertToCategory(type,it) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}