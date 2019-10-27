package com.my_project.moviesapp.di

import com.my_project.moviesapp.di.modules.NetworkModule
import com.my_project.moviesapp.di.modules.ProviderModule
import com.my_project.moviesapp.di.modules.RepositoryModule
import com.my_project.moviesapp.di.modules.RouterModule
import com.my_project.moviesapp.presentation.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
@Singleton
@Component
    (
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ProviderModule::class,
        RouterModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}