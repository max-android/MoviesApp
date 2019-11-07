package com.my_project.moviesapp.data.network

import android.content.Context
import com.my_project.moviesapp.utilities.ApiConst
import com.my_project.moviesapp.utilities.NetworkUtil
import okhttp3.Interceptor
import java.net.ConnectException

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
class InterceptorManager(val context: Context) {

    fun connectInterceptor() = Interceptor { chain ->
        if (!NetworkUtil.isOnline(context)) {
            throw ConnectException()
        }
        val builder = chain.request().newBuilder()
        val responce = chain.proceed(builder.build())
        responce
    }


    fun headerInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
                          .addHeader("Authorization",  ApiConst.API_KEY)
                          .build()
                  chain.proceed(newRequest)

         //TODO убрать
//        val original = chain.request()
//        val originalHttpUrl = original.url
//        val url = originalHttpUrl.newBuilder().build()
//        val requestBuilder = original.newBuilder().url(url)
//        val valueAuthorization = ApiConst.API_KEY
//        requestBuilder.addHeader("Authorization", valueAuthorization)
//        val request = requestBuilder.build()
//        chain.proceed(request)
    }

}