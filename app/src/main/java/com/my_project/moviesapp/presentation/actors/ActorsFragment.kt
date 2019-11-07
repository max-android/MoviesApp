package com.my_project.moviesapp.presentation.actors

import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.provider.ResourceProvider
import com.my_project.moviesapp.presentation.base.BaseFragment
import com.my_project.moviesapp.utilities.mainActivity
import javax.inject.Inject

/**
 * Created Максим on 03.11.2019.
 * Copyright © Max
 */
class ActorsFragment: BaseFragment() {

    companion object {
        const val ACTORS_KEY = "actors_key"
        @JvmStatic
        fun newInstance(): Fragment = ActorsFragment()
    }

    @Inject
    lateinit var rProvider: ResourceProvider
    override fun getLayoutRes(): Int = R.layout.fragment_actors

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init(){
        updateToolBar()
    }

    private fun updateToolBar(){
        mainActivity?.apply {
            updateToolBar(false)
            supportActionBar?.title = rProvider.getString(R.string.actors_item)
            toolBar.findViewById<AppCompatTextView>(R.id.titleTextView).text =
                rProvider.getString(R.string.actors_item)
        }
    }

}