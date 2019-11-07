package com.my_project.moviesapp.data.entities.category_movies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
@Parcelize
data class Movie (
    val results:List<BaseMovie>
): Parcelable,MovieInterface