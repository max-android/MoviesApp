package com.my_project.moviesapp.di.modules

import com.my_project.moviesapp.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository() = MainRepository()


    //@Provides
    //    @Singleton
    //    fun provideLoginRepository(api: ApiService, dataHolder: UserDataHolder)
    //    = LoginRepository(api,dataHolder)
}