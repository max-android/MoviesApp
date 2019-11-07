package com.my_project.moviesapp.presentation.category_movies

import com.my_project.moviesapp.data.entities.category_movies.Category

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
sealed class CategoryMoviesState
object Loading: CategoryMoviesState()
class SuccessCategoryMovies(val movies:Category): CategoryMoviesState()
class ErrorCategoryMovies(val error: Throwable): CategoryMoviesState()