package com.my_project.moviesapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.my_project.moviesapp.data.network.InterceptorManager
import com.my_project.moviesapp.data.network.MoviesService
import com.my_project.moviesapp.utilities.ApiConst
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * Created Максим on 05.11.2019.
 * Copyright © Max
 */
@Module
class MovieNetworkModule {

    @Provides
    @Named("Movie")
    fun provideOkHttpClientBuilder(interceptor: InterceptorManager): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                addInterceptor(interceptor.connectInterceptor())
                connectTimeout(ApiConst.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                readTimeout(ApiConst.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                writeTimeout(ApiConst.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            }.build()


    @Provides
    @Named("Movie")
    fun provideGson(): Gson = GsonBuilder().setDateFormat(ApiConst.DATE_TIME_FORMAT).create()


    @Provides
    @Named("Movie")
    fun provideRetrofit(@Named("Movie") gson: Gson,@Named("Movie") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(ApiConst.MOVIE_BASE_URL)
            client(okHttpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()


    @Provides
    fun provideMoviesService(@Named("Movie") movieRetrofit: Retrofit): MoviesService =
        movieRetrofit.create(MoviesService::class.java)

}