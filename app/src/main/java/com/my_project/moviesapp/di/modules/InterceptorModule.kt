package com.my_project.moviesapp.di.modules

import android.content.Context
import com.my_project.moviesapp.data.network.InterceptorManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 05.11.2019.
 * Copyright © Max
 */
@Module
class InterceptorModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideInterceptorManager() = InterceptorManager(context)

}