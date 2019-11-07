package com.my_project.moviesapp.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.my_project.moviesapp.R
import kotlinx.android.synthetic.main.toolbar_main.*

/**
 * Created Максим on 03.11.2019.
 * Copyright © Max
 */
open class DrawerActivity: AppCompatActivity() {

    companion object {
        const val MOVIES_ITEM = 1
        const val ACTORS_ITEM = 2
    }

    lateinit var toolBar: Toolbar
    lateinit var drawer: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initDrawer()
    }

    private fun  initToolbar(){
        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(mainToolBar)
    }

    private fun  initDrawer() {

        drawer = DrawerBuilder().apply {
                withActivity(this@DrawerActivity)
                withActionBarDrawerToggle(true)
                withActionBarDrawerToggleAnimated(true)
                withHeaderDivider(true)
                withTranslucentStatusBar(true)
                withToolbar(toolBar)
                withHeader(R.layout.drawer_header)

                addDrawerItems(
                    PrimaryDrawerItem()
                        .withIdentifier(MOVIES_ITEM.toLong())
                        .withName(R.string.main_item)
                        .withIcon(R.drawable.ic_main)
                        .withTextColor(ContextCompat.getColor(this@DrawerActivity, R.color.gray))
                        .withTypeface(ResourcesCompat.getFont(this@DrawerActivity, R.font.roboto_regular))
                        .withIconTintingEnabled(true),

                    PrimaryDrawerItem()
                        .withIdentifier(ACTORS_ITEM.toLong())
                        .withName(R.string.actors_item)
                        .withIcon(R.drawable.ic_recent_actors)
                        .withTextColor(ContextCompat.getColor(this@DrawerActivity, R.color.gray))
                        .withTypeface(ResourcesCompat.getFont(this@DrawerActivity, R.font.roboto_regular))
                        .withIconTintingEnabled(true)

                )
            }.build()
    }

}

