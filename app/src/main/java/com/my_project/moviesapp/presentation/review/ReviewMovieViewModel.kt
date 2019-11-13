package com.my_project.moviesapp.presentation.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_project.moviesapp.App
import com.my_project.moviesapp.data.repository.review.ReviewMovieRepository
import com.my_project.moviesapp.router.Router
import com.my_project.moviesapp.router.Screen
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/**
 * Created Максим on 11.11.2019.
 * Copyright © Max
 */
class ReviewMovieViewModel:ViewModel() {

    @Inject
    lateinit var rRepository: ReviewMovieRepository
    @Inject
    lateinit var router: Router
    private val subscriptions = CompositeDisposable()
    val rLiveData = MutableLiveData<ReviewMovieState>()

    init {
        App.appComponent.inject(this)
    }

    fun reviews(id:String) {
        rRepository
            .reviews(id)
            .doOnSubscribe { startProgress() }
            .subscribe(
                { review -> rLiveData.value = SuccessReviewMovie(review.results) },
                { error ->
                    run {
                        rLiveData.value = ErrorReviewMovie(error)
                        rLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }

    fun showWeb(url:String){
        router.forward(Screen.WEB_REVIEW,url)
    }

    private fun startProgress() {
        rLiveData.postValue(LoadingReviewMovie)
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}