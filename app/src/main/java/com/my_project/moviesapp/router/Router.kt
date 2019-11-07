package com.my_project.moviesapp.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.my_project.moviesapp.R
import com.my_project.moviesapp.presentation.actors.ActorsFragment
import com.my_project.moviesapp.presentation.movies.MoviesFragment
import timber.log.Timber

/**
 * Created Максим on 03.11.2019.
 * Copyright © Max
 */
class Router {

    private var fragmentManager: FragmentManager? = null
    var actualScreen: Screen = Screen.MOVIES

    fun init(fragmentManager: FragmentManager?) {
        this.fragmentManager = fragmentManager
    }

    fun replace(screen: Screen, data: Any = Any()) {
        //TODO
        Timber.tag("Router-backStackCount-1").d(backStackCount().toString())
        if (backStackCount() > 0) {
            clearBackStack()
        }
        //TODO
        Timber.tag("Router-backStackCount-2").d(backStackCount().toString())
        applyCommand(screen, Command.REPLACE, data)
    }

    fun forward(screen: Screen, data: Any = Any()) {
        applyCommand(screen, Command.FORWARD, data)
    }

    fun detach() {
        fragmentManager = null
    }

    private fun clearBackStack() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun back() {
        fragmentManager?.popBackStack()
        updateActualBackScreen()
    }

    fun backTo(nameFrag: String?) {
        fragmentManager?.popBackStackImmediate(nameFrag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun backStackCount() = fragmentManager?.backStackEntryCount ?: 0

    private fun applyCommand(screen: Screen, command: Command, data: Any) {
        updateActualScreen(screen)
        doFragmentTransaction(screen, command, data)
        Timber.tag("Router-actual-screen").d(actualScreen.toString())
    }

    private fun updateActualScreen(screen: Screen) {
        actualScreen = screen
    }

    private fun doFragmentTransaction(screen: Screen, command: Command, data: Any) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.main_container, getFragment(screen, data))
            ?.apply { if (command == Command.FORWARD) addToBackStack(screen.name) }
            ?.setCustomAnimations(
                R.animator.slide_in_right,
                R.animator.slide_out_left,
                R.animator.slide_in_left,
                R.animator.slide_out_right
            )
            ?.commitAllowingStateLoss()
    }

    private fun getFragment(screen: Screen, data: Any): Fragment {
        return when (screen) {
            Screen.MOVIES -> MoviesFragment.newInstance()
            Screen.ACTORS -> ActorsFragment.newInstance()

        }
    }

    //TODO
    //данный метод нужен для того,чтобы onPrepareOptionsMenu обновляла меню по actualScreen
    //при расширении там будет больше кода и тут также надо будет добавлять код
    private fun updateActualBackScreen() {
        actualScreen = when (actualScreen) {
//            Screen.DELIVERY -> Screen.BASKET
//            Screen.SEARCH -> Screen.MENU
            else -> Screen.MOVIES
        }
    }
}