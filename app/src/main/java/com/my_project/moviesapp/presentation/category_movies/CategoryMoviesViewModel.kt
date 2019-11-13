package com.my_project.moviesapp.presentation.category_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_project.moviesapp.App
import com.my_project.moviesapp.data.entities.category_movies.BaseMovie
import com.my_project.moviesapp.data.entities.review.ReviewEntity
import com.my_project.moviesapp.data.entities.video.VideoEntity
import com.my_project.moviesapp.data.repository.category_movies.CategoryMoviesRepository
import com.my_project.moviesapp.router.Router
import com.my_project.moviesapp.router.Screen
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class CategoryMoviesViewModel : ViewModel() {

    @Inject
    lateinit var cvRepository: CategoryMoviesRepository
    @Inject
    lateinit var router: Router
    private val subscriptions = CompositeDisposable()
    val cLiveData = MutableLiveData<CategoryMoviesState>()
    //TODO  все переменные далее только для случаев с пагинацией
    var vmTotalPage = 0
    var vmCountPage = 1
    var vmListMovies = mutableListOf<BaseMovie>()
    //TODO если true - значит произошел вызов метода onDestroyView
    var statusDestroyView = false
    var listWithPagin = false

    init {
        App.appComponent.inject(this)
    }

    fun clearDataForPagin() {
        vmCountPage = 1
        vmListMovies.clear()
    }

    fun nowPlayingMovies(type: String) {
        cvRepository
            .nowPlayingMovies(type)
            .doOnSubscribe { startProgress() }
            .subscribe(
                { category -> cLiveData.value = SuccessCategoryMovies(category) },
                { error ->
                    run {
                        cLiveData.value = ErrorCategoryMovies(error)
                        cLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }

    fun popularMovies(type: String, page: Int) {
        cvRepository
            .popularMovies(type, page)
            .doOnSubscribe { startProgress() }
            .subscribe(
                { category -> cLiveData.value = SuccessCategoryMovies(category) },
                { error ->
                    run {
                        cLiveData.value = ErrorCategoryMovies(error)
                        cLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }

    fun topRatedMovies(type: String, page: Int) {
        cvRepository
            .topRatedMovies(type, page)
            .doOnSubscribe { startProgress() }
            .subscribe(
                { category -> cLiveData.value = SuccessCategoryMovies(category) },
                { error ->
                    run {
                        cLiveData.value = ErrorCategoryMovies(error)
                        cLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }


    fun upcomingMovies(type: String) {
        cvRepository
            .upcomingMovies(type)
            .doOnSubscribe { startProgress() }
            .subscribe(
                { category -> cLiveData.value = SuccessCategoryMovies(category) },
                { error ->
                    run {
                        cLiveData.value = ErrorCategoryMovies(error)
                        cLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }

    private fun startProgress() {
        cLiveData.postValue(Loading)
    }

    fun showVideos(video: VideoEntity) {
        Timber.tag("--VIDEO-showVideos:").i("showVideos")
        router.forward(Screen.VIDEO, video)
    }

    fun showReviews(review: ReviewEntity) {
        router.forward(Screen.REVIEW, review)
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}