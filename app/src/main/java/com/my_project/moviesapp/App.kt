package com.my_project.moviesapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen
import com.my_project.moviesapp.di.AppComponent
import com.my_project.moviesapp.di.DaggerAppComponent
import com.my_project.moviesapp.di.modules.NetworkModule
import com.my_project.moviesapp.di.modules.ProviderModule
import com.my_project.moviesapp.di.modules.RepositoryModule
import com.my_project.moviesapp.di.modules.RouterModule
import timber.log.Timber

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
        initAndroidThreeTen()
        initFresco()
        initTimber()
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
            .routerModule(RouterModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .providerModule(ProviderModule(this))
            .build()
    }

    private fun initAndroidThreeTen() = AndroidThreeTen.init(this)

    private fun initFresco() = Fresco.initialize(this)

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}