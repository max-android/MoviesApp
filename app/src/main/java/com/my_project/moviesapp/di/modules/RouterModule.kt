package com.my_project.moviesapp.di.modules

import com.my_project.moviesapp.router.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Module
class RouterModule {
    @Provides
    @Singleton
    fun provideRouter() = Router()
}