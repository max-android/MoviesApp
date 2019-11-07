package com.my_project.moviesapp.data.mapper

import com.my_project.moviesapp.data.entities.category_movies.Category
import com.my_project.moviesapp.data.entities.category_movies.Movie
import com.my_project.moviesapp.data.entities.category_movies.MovieInterface
import com.my_project.moviesapp.data.entities.category_movies.TotalMovie
import com.my_project.moviesapp.utilities.CATEGORY_EXC

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class CategoryMapper {

    companion object {
        fun convertToCategory(type: String, movie: MovieInterface) =
            when (movie) {
                is Movie -> Category(type, null, null, null, movie.results)
                is TotalMovie -> Category(
                    type, movie.page,
                    movie.total_results,
                    movie.total_pages,
                    movie.results
                )
                else -> throw Exception(CATEGORY_EXC)
            }
    }

}

