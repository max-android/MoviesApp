package com.my_project.moviesapp.utilities

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
class NetworkUtil {

    companion object {
        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
    }

}