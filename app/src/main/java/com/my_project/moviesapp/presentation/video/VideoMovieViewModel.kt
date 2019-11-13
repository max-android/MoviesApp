package com.my_project.moviesapp.presentation.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_project.moviesapp.App
import com.my_project.moviesapp.data.repository.video.VideoMovieRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/**
 * Created Максим on 09.11.2019.
 * Copyright © Max
 */
class VideoMovieViewModel : ViewModel() {

    @Inject
    lateinit var vRepository: VideoMovieRepository
    private val subscriptions = CompositeDisposable()
    val vLiveData = MutableLiveData<VideoMovieState>()

    init {
        App.appComponent.inject(this)
    }

    fun videos(id:String) {
        vRepository
            .videos(id)
            .doOnSubscribe { startProgress() }
            .subscribe(
                { videos -> vLiveData.value = SuccessVideoMovie(videos.results) },
                { error ->
                    run {
                        vLiveData.value = ErrorVideoMovie(error)
                        vLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }

    private fun startProgress() {
        vLiveData.postValue(LoadingVideoMovie)
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}