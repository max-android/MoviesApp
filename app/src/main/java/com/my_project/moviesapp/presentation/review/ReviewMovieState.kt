package com.my_project.moviesapp.presentation.review

import com.my_project.moviesapp.data.entities.review.ReviewMovie

/**
 * Created Максим on 11.11.2019.
 * Copyright © Max
 */
sealed class ReviewMovieState
object LoadingReviewMovie : ReviewMovieState()
class SuccessReviewMovie(val reviews: List<ReviewMovie>) : ReviewMovieState()
class ErrorReviewMovie(val error: Throwable) : ReviewMovieState()



