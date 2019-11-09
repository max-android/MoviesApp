package com.my_project.moviesapp.presentation.movies

import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.my_project.moviesapp.App
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.provider.ResourceProvider
import com.my_project.moviesapp.presentation.adapters.CategoryPagerAdapter
import com.my_project.moviesapp.presentation.base.BaseFragment
import com.my_project.moviesapp.presentation.category_movies.CategoryMoviesFragment
import com.my_project.moviesapp.utilities.mainActivity
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

/**
 * Created Максим on 03.11.2019.
 * Copyright © Max
 */
class MoviesFragment : BaseFragment() {

    companion object {
        const val MOVIES_KEY = "movies_key"
        const val TAB_ITEM_FIRST = 0
        const val TAB_ITEM_SECOND = 1
        const val TAB_ITEM_THIRD = 2
        const val TAB_ITEM_FOURTH = 3
        @JvmStatic
        fun newInstance(): Fragment = MoviesFragment()
    }

    @Inject
    lateinit var rProvider: ResourceProvider
    private lateinit var viewModel: MoviesViewModel
    private lateinit var cPagerAdapter: CategoryPagerAdapter
    private lateinit var  titles: List<String>
    private lateinit var titleTextView:AppCompatTextView

    override fun getLayoutRes(): Int = R.layout.fragment_movies

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        init()
    }

    private fun init() {
        initTitles()
        updateToolBar()
        initViewPager()
        setData()
    }

    private fun initTitles(){
        titles = rProvider.getStringArray(R.array.arrays)
    }

    private fun updateToolBar() {
        mainActivity?.apply {
            updateToolBar(false)
            titleTextView = toolBar.findViewById<AppCompatTextView>(R.id.titleTextView)
                titleTextView.text = rProvider.getString(R.string.empty_text)
        }
    }

    private fun initViewPager() {
        cPagerAdapter = CategoryPagerAdapter(childFragmentManager)
        moviesViewPager.adapter = cPagerAdapter
        menuTabLayout.setupWithViewPager(moviesViewPager)
        menuTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    updateTitle(it.position)
                    updateScreen(it)
                }
            }

        })
    }


    private fun setData() = titles.forEach {
        //TODO убрать
       // if(it=="Популярные")
       // if(it == "Сейчас в кино")
        addMenuToAdapter(it)
    }

    private fun addMenuToAdapter(type: String) {
        cPagerAdapter.addTypesMenu(
            CategoryMoviesFragment.newInstance(type), type
        )
        cPagerAdapter.notifyDataSetChanged()
    }

    private fun updateTitle(item:Int){
        when(item){
            TAB_ITEM_FIRST -> titleTextView.text = titles[0]
            TAB_ITEM_SECOND -> titleTextView.text = titles[1]
            TAB_ITEM_THIRD -> titleTextView.text = titles[2]
            TAB_ITEM_FOURTH -> titleTextView.text = titles[3]
        }
    }

    private fun updateScreen(tab: TabLayout.Tab){
        moviesViewPager.currentItem = tab.position
    }

}