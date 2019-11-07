package com.my_project.moviesapp.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class CategoryPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentsList = mutableListOf<Fragment>()
    private val tabTitlesList = mutableListOf<String>()

    override fun getItem(position: Int): Fragment {
        return  fragmentsList[position]
    }

    override fun getCount() = tabTitlesList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitlesList[position]
    }

    fun addTypesMenu(fragment: Fragment, title: String) {
        fragmentsList.add(fragment)
        tabTitlesList.add(title)
    }
}