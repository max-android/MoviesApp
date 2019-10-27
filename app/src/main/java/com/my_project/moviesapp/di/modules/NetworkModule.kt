package com.my_project.moviesapp.di.modules

import com.google.gson.GsonBuilder
import com.my_project.moviesapp.data.network.InterceptorManager
import com.my_project.moviesapp.data.network.KinoInfoService
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
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: InterceptorManager): OkHttpClient =
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
    @Singleton
    fun provideGsonBuilder(): GsonBuilder {
        return GsonBuilder().setDateFormat(ApiConst.DATE_TIME_FORMAT)
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient, gsonBuilder: GsonBuilder):Retrofit.Builder =
        Retrofit.Builder()
            .apply {
                client(okHttpClient)
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            }

    @Provides
    @Singleton
    fun provideKinoInfoRetrofit(retrofitBuilder:Retrofit.Builder): Retrofit =
        retrofitBuilder.baseUrl(ApiConst.KINOINFO_BASE_URL).build()

    @Provides
    @Singleton
    fun provideMovieRetrofit(retrofitBuilder:Retrofit.Builder): Retrofit =
        retrofitBuilder.baseUrl(ApiConst.MOVIE_BASE_URL).build()

    @Provides
    @Singleton
    fun provideKinoInfoService(retrofit: Retrofit): KinoInfoService =
        retrofit.create(KinoInfoService::class.java)

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

}