package com.my_project.moviesapp.di.modules

import android.content.Context
import com.my_project.moviesapp.data.provider.AppInfoProvider
import com.my_project.moviesapp.data.provider.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Module
class ProviderModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppInfoProvider() = AppInfoProvider(context)

    @Provides
    @Singleton
    fun provideResourceProvider() = ResourceProvider(context)
}