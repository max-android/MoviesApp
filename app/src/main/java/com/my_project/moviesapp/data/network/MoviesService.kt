package com.my_project.moviesapp.data.network

import com.my_project.moviesapp.data.entities.category_movies.Movie
import com.my_project.moviesapp.data.entities.category_movies.TotalMovie
import com.my_project.moviesapp.utilities.ApiConst
import com.my_project.moviesapp.utilities.ApiConst.RU
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
interface MoviesService {

    //https://api.themoviedb.org/3/movie/now_playing?&language=ru&api_key=befc7872520fd736c58948abb2f4a53c
    //https://api.themoviedb.org/3/movie/now_playing?language=ru&api_key=befc7872520fd736c58948abb2f4a53c
    @GET("now_playing?")
    fun nowPlayingMovies(
        @Query("language") language: String = RU,
        @Query("api_key") api_key: String = ApiConst.API_KEY
    ): Single<Movie>

    //https://api.themoviedb.org/3/movie/popular?page=100&language=ru&api_key=befc7872520fd736c58948abb2f4a53c
    @GET("popular?")
    fun popularMovies(
        @Query("page") page: String,
        @Query("language") language: String = RU,
        @Query("api_key") api_key: String = ApiConst.API_KEY
    ): Single<TotalMovie>


    //https://api.themoviedb.org/3/movie/top_rated?language=ru&api_key=befc7872520fd736c58948abb2f4a53c
    @GET("top_rated?")
    fun topRatedMovies(
        @Query("page") page: String,
        @Query("language") language: String = RU,
        @Query("api_key") api_key: String = ApiConst.API_KEY
    ): Single<TotalMovie>


    //https://api.themoviedb.org/3/movie/upcoming?language=ru&api_key=befc7872520fd736c58948abb2f4a53c
    @GET("upcoming?")
    fun upcomingMovies(
        @Query("language") language: String = RU,
        @Query("api_key") api_key: String = ApiConst.API_KEY
    ): Single<Movie>


    //РАЗДЕЛ movies

    //Path Params - movie_id
    //https://api.themoviedb.org/3/movie/475557/videos?api_key=befc7872520fd736c58948abb2f4a53c
    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US
    //"key": "t433PEQGErc" - ключ для yuoyobe

    //Get Translations

    //ревью на фильм
    //https://api.themoviedb.org/3/movie/475557/reviews?api_key=befc7872520fd736c58948abb2f4a53c


    //https://api.themoviedb.org/3/movie/upcoming?region=RU&api_key=befc7872520fd736c58948abb2f4a53c

}