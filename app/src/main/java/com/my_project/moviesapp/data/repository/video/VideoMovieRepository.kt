package com.my_project.moviesapp.data.repository.video

import com.my_project.moviesapp.data.network.MoviesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created Максим on 09.11.2019.
 * Copyright © Max
 */
class VideoMovieRepository @Inject constructor(private val api: MoviesService) {

    fun videos(id:String) = api.videos(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}