package com.my_project.moviesapp.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.my_project.moviesapp.App
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.provider.ResourceProvider
import com.my_project.moviesapp.presentation.base.DrawerActivity
import com.my_project.moviesapp.router.Router
import com.my_project.moviesapp.router.Screen
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DrawerActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var rProvider: ResourceProvider
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        router.init(supportFragmentManager)
        if (savedInstanceState == null) { viewModel.showMovies() }
        init()
    }

    override fun onBackPressed() = when (router.actualScreen) {
        Screen.MOVIES,Screen.ACTORS -> if (drawer.isDrawerOpen) drawer.closeDrawer() else super.onBackPressed()
//        Screen.DELIVERY -> {
//            Timber.tag("--PRESS").i("Screen.DELIVERY")
//            if (drawer.isDrawerOpen) drawer.closeDrawer() else viewModel.back()
//        }
    }

    override fun onDestroy() {
        router.detach()
        super.onDestroy()
    }

    private fun init(){
        setDrawerListener()
    }

    private fun setDrawerListener(){
        drawer.onDrawerNavigationListener = object : Drawer.OnDrawerNavigationListener {
            override fun onNavigationClickListener(clickedView: View): Boolean {
                onBackPressed()
                return true
            }
        }

        drawer.onDrawerItemClickListener = object : Drawer.OnDrawerItemClickListener {
            override fun onItemClick(
                view: View?,
                position: Int,
                drawerItem: IDrawerItem<*>
            ): Boolean {
                selectItemDrawer(position)
                return false
            }
        }
    }

    private fun selectItemDrawer(item: Int) {
        when (item) {

            MOVIES_ITEM -> {
                viewModel.showMovies()
                Timber.tag("--PRESS").i("MOVIES_ITEM")
            }

            ACTORS_ITEM -> {
                viewModel.showActors()
            }
        }
    }

    fun updateToolBar(arrow: Boolean) {
        //TODO убрать
        // supportActionBar?.title = titleToolbar
        if (arrow) showBackArrow() else showHamburgerIcon()
    }

    private fun showBackArrow() {
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolBar.navigationIcon = rProvider.getDrawable(R.drawable.ic_arrow_back)
        Timber.tag("--PRESS").i("showBackArrow()")
    }

    private fun showHamburgerIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        Timber.tag("--PRESS").i("showHamburgerIcon()")
    }


}
