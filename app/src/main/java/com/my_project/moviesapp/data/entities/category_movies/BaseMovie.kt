package com.my_project.moviesapp.data.entities.category_movies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
@Parcelize
data class BaseMovie(
    val id:Int,
    val vote_average:Double,
    val title:String,
    val release_date:String,
    val backdrop_path:String?,
    val overview:String,
    val poster_path:String?
): Parcelable




