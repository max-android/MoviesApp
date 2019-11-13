package com.my_project.moviesapp.data.repository.review

import com.my_project.moviesapp.data.network.MoviesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created Максим on 11.11.2019.
 * Copyright © Max
 */
class ReviewMovieRepository @Inject constructor(private val api: MoviesService) {

    fun reviews(id:String) = api.reviews(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}