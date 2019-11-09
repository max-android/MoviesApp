package com.my_project.moviesapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.my_project.moviesapp.utilities.DialogUtils
import com.my_project.moviesapp.utilities.gone
import com.my_project.moviesapp.utilities.visible
import kotlinx.android.synthetic.main.preloader.*
import timber.log.Timber
import java.net.ConnectException

/**
 * Created Максим on 03.11.2019.
 * Copyright © Max
 */
abstract class BaseFragment: Fragment()  {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    protected fun showProgress() = preloaderLinearLayout.visible()

    protected fun removeProgress() = preloaderLinearLayout.gone()

    open fun showBaseError(error: Throwable) {
        Timber.e(error)
        removeProgress()
        if (error is ConnectException)
            DialogUtils(context!!).showNoConnect()
    }
}