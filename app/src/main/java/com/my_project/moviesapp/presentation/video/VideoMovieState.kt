package com.my_project.moviesapp.presentation.video

import com.my_project.moviesapp.data.entities.video.VideoMovie

/**
 * Created Максим on 09.11.2019.
 * Copyright © Max
 */
sealed class VideoMovieState
object LoadingVideoMovie:VideoMovieState()
class  SuccessVideoMovie(val videos:List<VideoMovie>):VideoMovieState()
class  ErrorVideoMovie(val error: Throwable):VideoMovieState()

